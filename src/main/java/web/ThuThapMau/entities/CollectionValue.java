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
    private Long form_value_id;
    private String form_value;
    private Long form_submit_time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "form_id", insertable = false, updatable = false)
    @JoinColumn(name = "form_attribute_id", insertable = false, updatable = false)
    private CollectionAttribute form_attribute;

}
