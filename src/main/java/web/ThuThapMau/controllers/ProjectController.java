package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.services.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjectByUserId(@RequestParam Long user_id) {
        List<Project> projects = projectService.getAllProjectByUserId(user_id);
        return ResponseEntity.status(200).body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getProjectByProjectId(@PathVariable(name = "id") Long project_id) {
        Optional<Project> project = projectService.getProjectByProjectId(project_id);
        return ResponseEntity.status(200).body(project);
    }

    @PostMapping
    public ResponseEntity<Project> createUser(@RequestBody Project newProject) {
        Project project = projectService.createProject(newProject);
        return ResponseEntity.status(201).body(project);
    }
}
