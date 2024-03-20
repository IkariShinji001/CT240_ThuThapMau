package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.repositories.ProjectMemberRepository;

import java.util.List;

@Service
public class ProjectMemberService {

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    public List<ProjectMember> getMemberByProjectId(Long project_id){
        return projectMemberRepository.getMembersByProjectId(project_id);
    }

    public void addMemberToProject(ProjectMember projectMember){
        projectMemberRepository.save(projectMember);
    }

    public void updateMemberStatus(ProjectMember payload){
        Long project_id = payload.getId().getProject().getProject_id();
        Long user_id = payload.getId().getUser().getUser_id();
        Long accept_status = (long) payload.getAccept_status();

        System.out.println(project_id + " " + user_id + " " +  accept_status);
        projectMemberRepository.updateMemberStatus(project_id, user_id, accept_status);
    }
}
