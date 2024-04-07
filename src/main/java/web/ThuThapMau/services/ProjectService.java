package web.ThuThapMau.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.ProjectRepository;
import web.ThuThapMau.repositories.UserRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

    public List<Project> getAllPersonalProject(Long userId) {
        return projectRepository.findAllPersonalProjectByUserId(userId);
    }

    public List<Object> getAllRequestJoinToProject(Long user_id){
        return  projectRepository.getAllRequestJoinToProject(user_id);
    }

    public Project findByInviteCode(UUID inviteCode){
        return projectRepository.findByUUID(inviteCode);
    }

    public List<Project> getAllProjectByUserId(Long user_id, Long accept_status){
        return projectRepository.findAllByUserId(user_id, accept_status);
    }
    public  List<Project> getAllNotificationsByUserId(Long user_id, Long accept_status){
        return  projectRepository.findAllNotiByUserId(user_id, accept_status);
    }
    public List<Project> getAllProjectByUserIdAndName(Long user_id, String project_name,Long accept_status){
        return projectRepository.findAllProjectByUserIdAndName(user_id, project_name, accept_status);
    }

    public Boolean checkOwnerProject(Long user_id, Long project_id){
        Project projectOwner = projectRepository.checkOwnerProject(user_id, project_id);
        return projectOwner != null;
    }


    public void updateProjectById(Long project_id, Project payload){
        String projectName = payload != null ? payload.getProject_name() : null;
        String projectStatus = payload != null ? payload.getProject_status() : null;
        projectRepository.updateProjectById(project_id, projectName, projectStatus);
    }

    public Project createProject(
            String project_name,
            String project_status,
            String project_created_at,
            MultipartFile file,
            String user_id){
        BlockingQueue<String> shareSecureUrlQueue = new ArrayBlockingQueue<>(1);

        Thread uploadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Luong thu 1");
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String secureUrl = (String)  uploadResult.get("secure_url");
                    shareSecureUrlQueue.put(secureUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        uploadThread.start();

        try{
            System.out.println("Luong thu 2");
            Project newProject = new Project();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date create = dateFormat.parse(project_created_at);
            User usertemp = userService.getUserById(Long.parseLong(user_id)).get();

            newProject.setUser(usertemp);
            newProject.setProject_created_at(create);
            newProject.setProject_name(project_name);
            newProject.setProject_status(project_status);
            System.out.println(newProject);

            String secureUrl = shareSecureUrlQueue.take();
            newProject.setProject_image_url(secureUrl);
            projectRepository.save(newProject);
            return newProject;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Project getProjectByProjectId(Long project_id){
        return projectRepository.findProjectById(project_id);
    }





}
