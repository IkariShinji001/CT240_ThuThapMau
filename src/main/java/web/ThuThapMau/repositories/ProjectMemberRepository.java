package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.ProjectMember;
import web.ThuThapMau.entities.compositeKeyId.ProjectMemberId;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    // Custom methods if needed
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.id.project.project_id = :project_id")
    List<ProjectMember> getMembersByProjectId(Long project_id);

}