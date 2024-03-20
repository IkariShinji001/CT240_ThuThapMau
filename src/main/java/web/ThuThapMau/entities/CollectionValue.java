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
@Entity
public class CollectionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collection_value_id;
    private String collection_value;
    private Long submit_time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_attribute_id")
    private CollectionAttribute form_attribute;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_form_id")
    private CollectionForm collection_form;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
