package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.ThuThapMau.dtos.ProjectDto;

import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Collection;

import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.ProjectService;

import java.util.ArrayList;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public ResponseEntity<List<ProjectDto>> getAllProjectByUserId(@PathVariable(name = "id") Long user_id, @RequestParam(required = false) String project_name, @RequestParam Long accept_status) {
        List<ProjectDto> projectDtos = new ArrayList<>();
        List<Project> projects;
        if (project_name != null && !project_name.isEmpty()) {
            projects = projectService.getAllProjectByUserIdAndName(user_id, project_name, accept_status);
        } else {
            projects = projectService.getAllProjectByUserId(user_id, accept_status);
        }
        for (Project project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setProject_id(project.getProject_id());
            projectDto.setProject_name(project.getProject_name());
            projectDto.setProject_created_at(project.getProject_created_at());
            projectDto.setUser_id(project.getUser().getUser_id());
            projectDtos.add(projectDto);
        }
        return ResponseEntity.status(200).body(projectDtos);
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
    public ResponseEntity<Project> uploadData(
            @RequestPart("project_name") String project_name,
            @RequestPart("project_status") String project_status,
            @RequestPart("project_created_at") String project_created_at,
            @RequestPart("file") MultipartFile file) {
        try {
            // Tải ảnh lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = (String) uploadResult.get("secure_url");
            Project newProject = new Project();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date create = dateFormat.parse(project_created_at);

            newProject.setProject_name(project_name);
            newProject.setProject_status(project_status);
            newProject.setProject_image_url(secureUrl);
            newProject.setProject_created_at(create);


            return ResponseEntity.ok(newProject);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
