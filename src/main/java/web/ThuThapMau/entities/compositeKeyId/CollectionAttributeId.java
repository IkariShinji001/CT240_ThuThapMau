package web.ThuThapMau.entities.compositeKeyId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;

@Embeddable
public class CollectionAttributeId implements Serializable {

    @Column(name = "form_id")
    private CollectionForm form_id;

    @Column(name = "form_attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long form_attribute_id;

}
