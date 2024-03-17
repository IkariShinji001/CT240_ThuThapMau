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
}
