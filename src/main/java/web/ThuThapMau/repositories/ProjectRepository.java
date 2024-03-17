package web.ThuThapMau.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.Project;

import java.util.List;
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Modifying
    @Transactional
    @Query("SELECT p FROM Project p WHERE p.user.user_id = :user_id")
    List<Project> findAllByUserId(Long user_id);

    @Query("SELECT p FROM Project p WHERE p.user.user_id = :user_id AND p.project_name LIKE '%:project_name%'")
    List<Project> findAllProjectByUserIdAndName(Long user_id, String project_name);
    @Modifying  // Optional for UPDATE queries
    @Transactional
    @Query("UPDATE Project p SET p.project_name = :project_name, p.project_status = :project_status WHERE p.project_id = :project_id")
    void updateProjectById(Long project_id, String project_name, String project_status);
}
