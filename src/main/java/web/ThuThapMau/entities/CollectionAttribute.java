package web.ThuThapMau.entities;

import jakarta.persistence.*;
import web.ThuThapMau.entities.compositeKeyId.CollectionAttributeId;

import java.util.List;

@Entity(name = "CollectionAttribute")
public class CollectionAttribute {
    @EmbeddedId
    private CollectionAttributeId id;
    private String form_attribute_name;
    @ManyToOne
    @JoinColumn(name = "form_id", insertable = false, updatable = false)
    private CollectionForm form;

}
