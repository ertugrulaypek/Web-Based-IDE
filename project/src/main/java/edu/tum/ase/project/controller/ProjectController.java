package edu.tum.ase.project.controller;

import edu.tum.ase.project.model.Project;
import edu.tum.ase.project.model.ShareProjectResponseModel;
import edu.tum.ase.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;


@RestController
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(path = "/project")
    public Project createProject(@RequestParam("project_name") String projectName){
        Project newProject = new Project(projectName);
        return projectService.createProject(newProject);
    }

    @GetMapping(path = "/project")
    public Project getProject(@RequestParam(value = "project_name") String projectName){
        return projectService.findByName(projectName);
    }

    @PostFilter ("filterObject.isAuthenticated(authentication.name)")
    @GetMapping(path = "/project/allProjects")
    public List<Project> getAllProjects(){
        return projectService.getProjects();
    }

    @PatchMapping(path = "/project")
    public Project updateProjectName(@RequestParam(value = "project_name") String projectName,
                                     @RequestParam(value = "new_project_name") String newProjectName) {
        return projectService.updateProjectName(projectName, newProjectName);
    }

    @DeleteMapping(path = "/project")
    public Project deleteProject(@RequestParam(value = "project_name") String projectName){
        return projectService.deleteProject(projectName);
    }

    @PutMapping(path = "/project/{id}/share/{username}")
    public ShareProjectResponseModel shareProject(@PathVariable("username") String username, @PathVariable("id") String id){
        return projectService.shareProject(username, id);
    }

}
