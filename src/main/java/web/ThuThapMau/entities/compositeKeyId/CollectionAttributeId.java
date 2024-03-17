package web.ThuThapMau.entities.compositeKeyId;

import jakarta.persistence.*;
import web.ThuThapMau.entities.CollectionForm;

import java.io.Serializable;

@Embeddable
public class CollectionAttributeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "form_id")
    private CollectionForm form;

    @Column(name = "form_attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long form_attribute_id;

    // Constructors, getters, and setters
}