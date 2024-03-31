package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.dtos.ProjectMemberRequestDto;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.services.ProjectMemberService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/project-member")
public class ProjectMemberController {
    @Autowired
    ProjectMemberService projectMemberService;
    @GetMapping("/projects/{id}")
    public ResponseEntity<List<ProjectMember>> getMemberByProjectId(@PathVariable(name = "id") Long project_id, @RequestParam Integer accept_status){
        List<ProjectMember> members = projectMemberService.getMemberByProjectId(project_id, accept_status);
        return ResponseEntity.status(200).body(members);
    }
    @PostMapping("/projects/{project_id}")
    public ResponseEntity<String> addMemberToProject(@PathVariable Long project_id, @RequestBody List<Long> user_ids){
        projectMemberService.addMemberToProject(project_id, user_ids);
        return ResponseEntity.status(200).body("OK");
    }

    @PostMapping("/owner-projects/{project_id}")
    public ResponseEntity<String> addOwnerToProjectMember(@PathVariable Long project_id, @RequestBody Long user_id){
        projectMemberService.addOwnerToProjectMember(project_id, user_id);
        return ResponseEntity.status(200).body("OK");
    }

    @DeleteMapping("/projects/{project_id}/users/{user_id}")
    public ResponseEntity<String> removeMemberFromProject(@PathVariable Long project_id, @PathVariable Long user_id){
        projectMemberService.removeMemberFromProject(project_id, user_id);
        return ResponseEntity.status(200).body("OK");
    }

    @PatchMapping
    public ResponseEntity<String>  updateMemberStatus(@RequestBody ProjectMemberRequestDto payload){
        projectMemberService.updateMemberStatus(payload);
        return ResponseEntity.status(200).body("OK");
    }
}
