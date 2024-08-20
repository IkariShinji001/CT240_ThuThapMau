package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.services.ProjectService;

import java.util.List;

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
        try {
            List<Project> projects;
            projects = projectService.getAllPersonalProject(user_id);

            return ResponseEntity.status(200).body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/users/noti/{id}")
    public ResponseEntity<List<Project>> getAllNotificationsByUserId(@PathVariable(name = "id") Long user_id, @RequestParam(required = false) String project_name, @RequestParam Long accept_status) {
        try {
            List<Project> projects;
            projects = projectService.getAllNotificationsByUserId(user_id, accept_status);
            return ResponseEntity.status(200).body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/users/{user_id}/request")
    public ResponseEntity<List<Object>> getAllRequestJoinToProject(@PathVariable Long user_id ){
        try {
//            danh sach cac project
            List<Object> request = projectService.getAllRequestJoinToProject(user_id);
            return ResponseEntity.status(200).body(request);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<List<Project>> getAllProjectByUserId(@PathVariable(name = "id") Long user_id, @RequestParam(required = false) String project_name, @RequestParam Long accept_status) {
        try {
            List<Project> projects;
            if (project_name != null && !project_name.isEmpty()) {
                System.out.println(project_name);
                projects = projectService.getAllProjectByUserIdAndName(user_id, project_name, accept_status);
            } else {
                projects = projectService.getAllProjectByUserId(user_id, accept_status);
            }
            return ResponseEntity.status(200).body(projects);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/{project_id}/users/{user_id}")
    public ResponseEntity<Boolean> checkProjectOwner(@PathVariable Long project_id, @PathVariable Long user_id) {
        try {
            Boolean isOwner = projectService.checkOwnerProject(user_id, project_id);
            return ResponseEntity.status(200).body(isOwner);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectByProjectId(@PathVariable(name = "id") Long project_id) {
        try {
            Project project = projectService.getProjectByProjectId(project_id);
            return ResponseEntity.status(200).body(project);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }


    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProjectById(@PathVariable(name = "id") Long project_id, @RequestBody Project payload) {
        try {
            projectService.updateProjectById(project_id, payload);
            return ResponseEntity.status(200).body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestPart("project_name") String project_name,
            @RequestPart("project_status") String project_status,
            @RequestPart("project_created_at") String project_created_at,
            @RequestPart("file") MultipartFile file,
            @RequestPart("user_id") String user_id) {
        try {
            Project project = projectService.createProject(project_name, project_status, project_created_at, file, user_id);
            return ResponseEntity.status(200).body(project);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }
}
