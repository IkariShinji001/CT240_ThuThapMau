package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.ThuThapMau.entities.CollectionValue;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionValueRequestBody {
    private Long user_id;
    private Long collection_form_id;
    List<ListValue> valueList;
}

