package web.ThuThapMau.entities.compositeKeyId;

import jakarta.persistence.*;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.entities.Project;

import java.io.Serializable;

@Embeddable
public class ProjectMemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name ="project_id")
    private  Project project;

}

