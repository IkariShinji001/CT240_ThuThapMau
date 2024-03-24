package web.ThuThapMau.dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.entities.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionValueDto {
    private Long collection_value_id;
    private String collection_value;
    private Long submit_time;
    private Long collection_attribute_id;;
    private Long collection_form_id;
    private Long user_id;

}
