package web.ThuThapMau.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity(name = "Project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    private String project_name;
    private String project_status;
    private Date project_created_at;
    private String project_image_url;
    private UUID inviteCode;

    @PrePersist
    private void generateInviteCode() {
        this.inviteCode = UUID.randomUUID();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
