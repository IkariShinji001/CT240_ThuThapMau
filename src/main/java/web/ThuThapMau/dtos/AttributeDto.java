package web.ThuThapMau.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.CollectionForm;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeDto {
    private Long collection_attribute_id;
    private String collection_attribute_name;
    private Long collection_form;
}
