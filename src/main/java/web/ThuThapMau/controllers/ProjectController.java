package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.services.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private Cloudinary cloudinary;

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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getProjectByProjectId(@PathVariable(name = "id") Long project_id) {
        Optional<Project> project = projectService.getProjectByProjectId(project_id);
        return ResponseEntity.status(200).body(project);
    }

//    @PostMapping
//    public ResponseEntity<Project> createProject(@RequestBody Project newProject){
//        System.out.println(newProject);
//        Project project = projectService.createProject(newProject);
//        return ResponseEntity.status(200).body(project);
//    }

    @PutMapping("/{id}")
    public ResponseEntity updateProjectById(@PathVariable(name = "id") Long project_id, @RequestBody Project payload){
        projectService.updateProjectById(project_id, payload);
        return ResponseEntity.status(200).body("Cập nhật thành công");

    }

    @PostMapping
    public ResponseEntity<Project> uploadData(@RequestPart("description") Project project,
                                                 @RequestPart("file") MultipartFile file) {
        try {
            // Tải ảnh lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = (String) uploadResult.get("secure_url");
            project.setProject_image_url(secureUrl);
            Project newProject = projectService.createProject(project);
            return ResponseEntity.ok(newProject);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
