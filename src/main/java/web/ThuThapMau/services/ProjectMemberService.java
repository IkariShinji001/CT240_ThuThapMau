package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.dtos.ProjectMemberRequestDto;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.repositories.ProjectMemberRepository;

import java.util.List;

@Service
public class ProjectMemberService {

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    public List<ProjectMember> getMemberByProjectId(Long project_id) {
        return projectMemberRepository.getMembersByProjectId(project_id);
    }

    public void addMemberToProject(Long user_id, ProjectMember projectMember) {
        projectMemberRepository.save(projectMember);
    }

    public void updateMemberStatus(ProjectMemberRequestDto payload) {
        Long project_id = payload.getProject_id();
        Long user_id =  payload.getUser_id();
        Long accept_status = payload.getAccept_status();
        projectMemberRepository.updateMemberStatus(project_id, user_id, accept_status);
    }
}
