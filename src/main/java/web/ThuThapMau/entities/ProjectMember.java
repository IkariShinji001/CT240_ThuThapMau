package web.ThuThapMau.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.compositeKeyId.ProjectMemberId;

@Entity(name = "ProjectMember")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProjectMember {
    @EmbeddedId
    private ProjectMemberId id;
}
