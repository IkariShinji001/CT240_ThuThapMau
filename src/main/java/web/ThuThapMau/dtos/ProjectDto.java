package web.ThuThapMau.dtos;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Long project_id;
    private String project_name;
    private Date project_created_at;
    private Long user_id;
}
