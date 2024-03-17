package web.ThuThapMau.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CollectionForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long form_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private Collection collection_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user_id;

    @OneToMany(mappedBy = "form_id")
    private List<CollectionAttribute> collection_attributes;

    @OneToMany(mappedBy = "form_id")
    private List<CollectionValue> collection_values;
}
