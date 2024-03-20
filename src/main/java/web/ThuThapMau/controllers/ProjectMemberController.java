package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.services.ProjectMemberService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/project-member")
public class ProjectMemberController {
    @Autowired
    ProjectMemberService projectMemberService;
    @GetMapping("/projects/{id}")
    public ResponseEntity<List<ProjectMember>> getMemberByProjectId(@PathVariable(name = "id") Long project_id){
        List<ProjectMember> members = projectMemberService.getMemberByProjectId(project_id);
        return ResponseEntity.status(200).body(members);
    }
    @PostMapping
    public ResponseEntity<String> addMemberToProject(@RequestBody ProjectMember payload){
        projectMemberService.addMemberToProject(payload);
        return ResponseEntity.status(200).body("OK");
    }

    @PatchMapping
    public ResponseEntity<String>  updateMemberStatus(@RequestBody ProjectMember payload){
        projectMemberService.updateMemberStatus(payload);
        return ResponseEntity.status(200).body("OK");
    }
}
