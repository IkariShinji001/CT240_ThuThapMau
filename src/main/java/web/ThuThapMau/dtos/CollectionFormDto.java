package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.CollectionAttribute;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionFormDto {
    private String collection_form_name;
    private Long user_id;
   private  Long collection_id;
   List<CollectionAttribute> attributeList;
}
