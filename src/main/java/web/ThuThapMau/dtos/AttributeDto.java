package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeDto {
    private Long form_attribute_id;
    private String form_attribute_name;
    private Long collection_form_id;
}
