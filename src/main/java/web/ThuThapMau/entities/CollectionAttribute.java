package web.ThuThapMau.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "CollectionAttribute")
public class CollectionAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collection_attribute_id;
    private String collection_attribute_name;

    @ManyToOne
    @JoinColumn(name = "collection_form_id")
    private CollectionForm collection_form;

}
