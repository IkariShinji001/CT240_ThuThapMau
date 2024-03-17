package web.ThuThapMau.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import web.ThuThapMau.entities.compositeKeyId.CollectionAttributeId;

@Entity(name = "CollectionAttribute")
public class CollectionAttribute {
    @EmbeddedId
    private CollectionAttributeId id;
    private String form_attribute_name;
    @ManyToOne
    @JoinColumn(name = "form_id", insertable = false, updatable = false)
    private CollectionForm collection_form;

}
