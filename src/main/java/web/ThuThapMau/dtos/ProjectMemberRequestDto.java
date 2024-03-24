package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberRequestDto {
    private Long user_id;
    private Long project_id;
    private Long accept_status;
}
