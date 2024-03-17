package web.ThuThapMau.entities.compositeKeyId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.entities.Project;

import java.io.Serializable;

@Embeddable
public class ProjectMemberId implements Serializable {
    @ManyToOne
    @Column(name ="user_id")
    private User user;

    @ManyToOne
    @Column(name ="project_id")
    private  Project project;

}

