package edu.tum.ase.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.ShareProjectResponseModel;
import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.model.SourceFileContent;
import edu.tum.ase.project.repository.ProjectRepository;
import edu.tum.ase.project.repository.SourceFileContentRepository;
import edu.tum.ase.project.repository.SourceFileRepository;
import org.bouncycastle.crypto.signers.ISOTrailers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SourceFileRepository sourceFileRepository;

    @Autowired
    private SourceFileContentRepository sourceFileContentRepository;


    @Autowired
    private OAuth2RestOperations restTemplate;

    private final String gitlabUrl = "https://gitlab.lrz.de/api/v4/";

    public Project createProject(Project project){
        ObjectNode resource = restTemplate.getForObject(gitlabUrl + "user", ObjectNode.class);
        JsonNode username = resource.findValue("username");
        project.getUserIds().add(username.textValue());
        return projectRepository.save(project);
    }

    public Project findByName(String name){
        return projectRepository.findByName(name);
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project deleteProject(String projectName){
        Project project = findByName(projectName);
        projectRepository.deleteById(project.getId());
        return project;
    }

    public Project updateProjectName(String oldName, String newName){
        Project project = findByName(oldName);
        project.setName(newName);
        return createProject(project);
    }

    public SourceFile createSourceFile(String projectName, String sourceFileName) {
        Project project = findByName(projectName);
        SourceFile sourceFile = new SourceFile(project, sourceFileName);
        SourceFile persistedSourceFile = sourceFileRepository.save(sourceFile);
        SourceFileContent emptyContent = new SourceFileContent(sourceFile, "");
        sourceFileContentRepository.save(emptyContent);
        return persistedSourceFile;
    }

    // To discuss
    public SourceFile getSourceFile(String projectName, String fileName) {
        Project project = findByName(projectName);
        List<SourceFile> sourceFiles = sourceFileRepository.findByProjectId(project.getId());
        Optional<SourceFile> sourceFileWithGivenName = sourceFiles.stream().filter(sourceFile -> sourceFile.getFileName().equals(fileName)).findFirst();
        return sourceFileWithGivenName.get();
    }

    public List<SourceFile> getAllFiles(String projectName) {
        Project project = findByName(projectName);
        return this.sourceFileRepository.findByProjectId(project.getId());
    }

    public SourceFile updateSourceFileName(String projectName, String fileName, String newFileName) {
        SourceFile sourceFile = getSourceFile(projectName, fileName);
        sourceFile.setFileName(newFileName);
        return sourceFileRepository.save(sourceFile);
    }

    public SourceFile deleteSourceFile(String projectName, String fileName) {
        SourceFile sourceFile = getSourceFile(projectName, fileName);
        sourceFileRepository.delete(sourceFile);
        return sourceFile;
    }

    public SourceFileContent getSourceFileContent(String projectName, String fileName){
        SourceFile sourceFile = getSourceFile(projectName, fileName);
        return sourceFileContentRepository.findById(sourceFile.getFileId()).get();
    }

    public SourceFileContent updateSourceFileContent(String projectName, String fileName, String content) {
        SourceFile sourceFile = getSourceFile(projectName, fileName);
        SourceFileContent sourceFileContent = getSourceFileContent(projectName, fileName);
        sourceFileContent.setFileContent(content);
        return sourceFileContentRepository.save(sourceFileContent);
    }

    public ShareProjectResponseModel shareProject(String username, String id){
        ShareProjectResponseModel response = new ShareProjectResponseModel();
        if(!restTemplate.getForObject(gitlabUrl + "users" + "?username=" + username, String.class).equals("[]")) {
            Project project = projectRepository.findById(id).get();
            if(!project.getUserIds().contains(username)){
                project.getUserIds().add(username);
                projectRepository.save(project);
                response.setResponse("user added");
            }
            else response.setResponse("user already added");
        }
        else response.setResponse("username does not exists");
        return response;
    }


}
