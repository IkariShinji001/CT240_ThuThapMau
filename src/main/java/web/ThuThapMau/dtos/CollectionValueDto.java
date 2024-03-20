package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionValueDto {
    private Long form_value_id;
    private String form_value;
    private Long form_submit_time;
    private Long form_attribute_id;
    private Long form_id;
    private Long user_id;
}
