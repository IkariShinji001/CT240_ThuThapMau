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
public class CollectionForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collection_form_id;
    String collection_form_name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
