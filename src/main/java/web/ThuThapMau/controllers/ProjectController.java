package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.ProjectDto;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.services.ProjectService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/personal/users/{id}")
    public ResponseEntity<List<Project>> getAllPersonalProjectByUserId(@PathVariable(name = "id") Long user_id) {
        List<Project> projects;
        projects = projectService.getAllPersonalProject(user_id);

        return ResponseEntity.status(200).body(projects);
    }

    @GetMapping("/users/noti/{id}")
    public ResponseEntity<List<Project>> getAllNotificationsByUserId(@PathVariable(name = "id") Long user_id, @RequestParam(required = false) String project_name,@RequestParam Long accept_status ){
        List<Project> projects;
        projects = projectService.getAllNotificationsByUserId(user_id, accept_status);

        return  ResponseEntity.status(200).body(projects);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Project>> getAllProjectByUserId(@PathVariable(name = "id") Long user_id, @RequestParam(required = false) String project_name, @RequestParam Long accept_status) {
        List<Project> projects;
        if (project_name != null && !project_name.isEmpty()) {
            System.out.println(project_name);
            projects = projectService.getAllProjectByUserIdAndName(user_id, project_name, accept_status);
        } else {
            projects = projectService.getAllProjectByUserId(user_id, accept_status);
        }
        return ResponseEntity.status(200).body(projects);
    }

    @GetMapping("/{project_id}/users/{user_id}")
    public ResponseEntity<Boolean> checkProjectOwner(@PathVariable Long project_id, @PathVariable Long user_id) {
        Boolean isOwner = projectService.checkOwnerProject(user_id, project_id);
        return ResponseEntity.status(200).body(isOwner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectByProjectId(@PathVariable(name = "id") Long project_id) {
        Project project = projectService.getProjectByProjectId(project_id);
        return ResponseEntity.status(200).body(project);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProjectById(@PathVariable(name = "id") Long project_id, @RequestBody Project payload) {
        projectService.updateProjectById(project_id, payload);
        return ResponseEntity.status(200).body("Cập nhật thành công");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestPart("project_name") String project_name,
            @RequestPart("project_status") String project_status,
            @RequestPart("project_created_at") String project_created_at,
            @RequestPart("file") MultipartFile file,
            @RequestPart("user_id") String user_id) {
       Project project = projectService.createProject(project_name, project_status, project_created_at, file, user_id);
       return  ResponseEntity.status(200).body(project);
    }
}
