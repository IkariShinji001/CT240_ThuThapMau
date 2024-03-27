package web.ThuThapMau.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "Project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long project_id;

    private String project_name;
//   trạng thái 1. set default là đang hoạt động khi được tạo
//   trạng thái 2. là dự án đã kết thúc
    private String project_status;
    private Date project_created_at;
    private String project_image_url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
