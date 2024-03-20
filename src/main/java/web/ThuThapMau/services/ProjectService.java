package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjectByUserId(Long user_id, Long accept_status){
        return projectRepository.findAllByUserId(user_id, accept_status);
    }

    public List<Project> getAllProjectByUserIdAndName(Long user_id, String project_name,Long accept_status){
        return projectRepository.findAllProjectByUserIdAndName(user_id, project_name, accept_status);
    }


    public void updateProjectById(Long project_id, Project payload){
        String projectName = payload != null ? payload.getProject_name() : null;
        String projectStatus = payload != null ? payload.getProject_status() : null;
        projectRepository.updateProjectById(project_id, projectName, projectStatus);
    }

    public Project createProject(Project newProject){
        return projectRepository.save(newProject);
    }


    public Optional<Project> getProjectByProjectId(Long project_id){
        return projectRepository.findById(project_id);
    }


}
