package web.ThuThapMau.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.entities.compositeKeyId.ProjectMemberId;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    // Custom methods if needed
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.id.project.project_id = :project_id")
    List<ProjectMember> getMembersByProjectId(Long project_id);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectMember pm SET pm.accept_status = :accept_status WHERE pm.id.project.project_id = :project_id AND pm.id.user.user_id = :user_id")
    void updateMemberStatus(Long project_id,Long user_id,Long accept_status);

//    @Query("SELECT pm FROM ProjectMember pm WHERE pm.id.project.project_id = :project_id")
//    int (Long project_id);
}