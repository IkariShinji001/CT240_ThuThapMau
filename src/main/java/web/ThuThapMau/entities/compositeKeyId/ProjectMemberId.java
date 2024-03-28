package web.ThuThapMau.entities.compositeKeyId;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.entities.Project;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ProjectMemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="project_id")
    private Project project;


}

