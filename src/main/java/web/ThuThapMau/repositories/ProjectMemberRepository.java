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
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.id.project.project_id = :project_id AND pm.accept_status = :accept_status")
    List<ProjectMember> getMembersByProjectId(Long project_id, Integer accept_status);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectMember pm SET pm.accept_status = :accept_status WHERE pm.id.project.project_id = :project_id AND pm.id.user.user_id = :user_id")
    void updateMemberStatus(Long project_id, Long user_id, Long accept_status);

    @Modifying
    @Transactional
    @Query("DELETE ProjectMember pm WHERE pm.id.project.project_id = :project_id AND pm.id.user.user_id = :user_id")
    void removeMemberFromProject(Long project_id, Long user_id);

    @Query("SELECT pm FROM ProjectMember pm JOIN Project p ON pm.id.project.project_id = p.project_id JOIN users u ON pm.id.user.user_id = u.user_id WHERE p.user.user_id = :user_id AND pm.accept_status = 1")
    List<ProjectMember> getNotificationRequest(Long user_id);


}