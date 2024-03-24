package web.ThuThapMau.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CollectionDTO {
    private Long collection_id;
    private String collection_name;
    private Date collection_start;
    private Date collection_end;
    private String collection_image_url;
    private String collection_description;
    private Date collection_created_at;
}
