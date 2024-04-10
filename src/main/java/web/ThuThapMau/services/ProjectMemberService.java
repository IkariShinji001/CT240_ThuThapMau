package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.dtos.ProjectMemberRequestDto;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.entities.compositeKeyId.ProjectMemberId;
import web.ThuThapMau.repositories.ProjectMemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectMemberService {

    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;

    public List<ProjectMember> getMemberByProjectId(Long project_id, Integer accept_status) {
        return projectMemberRepository.getMembersByProjectId(project_id, accept_status);
    }

    public void removeMemberFromProject(Long project_id, Long user_id) {
        System.out.println(project_id + " " + user_id);
        projectMemberRepository.removeMemberFromProject(project_id, user_id);
    }


    public void addMemberToProject(Long project_id, List<Long> user_ids) {
        Project project = projectService.getProjectByProjectId(project_id);
        user_ids.forEach((user_id) -> {
            ProjectMemberId id = new ProjectMemberId();
            Optional<User> user = userService.getUserById(user_id);
            id.setProject(project);
            id.setUser(user.get());
            ProjectMember newProjectMember = new ProjectMember();
            newProjectMember.setId(id);
            newProjectMember.setAccept_status(0);
            projectMemberRepository.save(newProjectMember);
        });
    }

    public void requestJoinToProject(Long user_id, UUID inviteCode){
        Project project = projectService.findByInviteCode(inviteCode);
        User user = userService.getUserById(user_id).get();
        ProjectMemberId id = new ProjectMemberId(user, project);
        ProjectMember newRequest = new ProjectMember();
        newRequest.setAccept_status(1);
        newRequest.setId(id);
        projectMemberRepository.save(newRequest);
    }

    public void updateMemberStatus(ProjectMemberRequestDto payload) {
        Long project_id = payload.getProject_id();
        Long user_id = payload.getUser_id();
        Long accept_status = payload.getAccept_status();
        System.out.println(project_id + " " + user_id + " " + accept_status);
        projectMemberRepository.updateMemberStatus(project_id, user_id, accept_status);
    }

    public void addOwnerToProjectMember(Long projectId, Long userId) {
        Project project = projectService.getProjectByProjectId(projectId);
        User user = userService.getUserById(userId).get();
        user.setUser_id(userId);
        ProjectMember newProjectMember = new ProjectMember();
        ProjectMemberId id = new ProjectMemberId();
        id.setProject(project);
        id.setUser(user);
        newProjectMember.setId(id);
        newProjectMember.setAccept_status(2);
        projectMemberRepository.save(newProjectMember);
    }


    public List<ProjectMember> getNotificationRequest(Long user_id){
        return projectMemberRepository.getNotificationRequest(user_id);
    }
}
