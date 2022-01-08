package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.SourceFile;
import edu.tum.ase.project.model.SourceFileContent;
import edu.tum.ase.project.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SourceFileController {

    private ProjectService projectService;

    public SourceFileController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/project/source")
    public SourceFile createSourceFile(@RequestParam(name = "source_file_name") String sourceFileName,
                                       @RequestParam(name = "project_name") String projectName){
        return projectService.createSourceFile(projectName, sourceFileName);
    }
    // To discuss
    @GetMapping(value = "/project/source")
    public SourceFile getSourceFile(@RequestParam(name = "source_file_name") String fileName,
                                    @RequestParam(name = "project_name") String projectName){
        return projectService.getSourceFile(projectName, fileName);
    }

    @GetMapping(value = "/project/source/getAllFiles")
    public List<SourceFile> getAllSourceFiles( @RequestParam(name = "project_name") String projectName){
        return projectService.getAllFiles(projectName);
    }

    @GetMapping(value = "/project/source/content")
    public SourceFileContent getSourceFileContent(@RequestParam(name = "source_file_name") String fileName,
                                                  @RequestParam(name = "project_name") String projectName){
        return projectService.getSourceFileContent(projectName, fileName);
    }



    @PatchMapping(value = "/project/source")
    public SourceFile updateSourceFileName(@RequestParam(name = "source_file_name") String fileName,
                                           @RequestParam(name = "new_source_file_name") String newFileName,
                                           @RequestParam(name = "project_name") String projectName) {
        return projectService.updateSourceFileName(projectName, fileName, newFileName);
    }

    @PatchMapping(value = "/project/source/content")
    public SourceFileContent updateSourceFileContent(@RequestParam(name = "source_file_name") String fileName,
                                                     @RequestParam(name = "project_name") String projectName,
                                                     @RequestParam(name = "content") String content){
        return projectService.updateSourceFileContent(projectName, fileName, content);
    }

    @DeleteMapping(value = "/project/source")
    public SourceFile deleteSourceFile(@RequestParam(name = "source_file_name") String fileName,
                                       @RequestParam(name = "project_name") String projectName) {
        return projectService.deleteSourceFile(projectName, fileName);
    }
}
