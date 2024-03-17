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

    public List<Project> getAllProjectByUserId(Long user_id){
        return projectRepository.findAllByUserId(user_id);
    }

    public Project createProject(Project newProject){
        return projectRepository.save(newProject);
    }

    public Optional<Project> getProjectByProjectId(Long project_id){
        return projectRepository.findById(project_id);
    }


}
