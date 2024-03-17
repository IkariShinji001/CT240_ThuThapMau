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
    private Collection collection;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "form")
    private List<CollectionAttribute> collection_attributes;

    @OneToMany(mappedBy = "form")
    private List<CollectionValue> collection_values;
}
