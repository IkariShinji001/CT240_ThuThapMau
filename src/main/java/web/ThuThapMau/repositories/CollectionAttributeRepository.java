package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.CollectionAttribute;

import java.util.List;
public interface CollectionAttributeRepository extends JpaRepository<CollectionAttribute, Long> {
        @Modifying
        @Query("SELECT p FROM CollectionAttribute p JOIN CollectionForm cf ON p.collection_form.collection_form_id = cf.collection_form_id WHERE p.collection_form.collection_form_id = :collection_form_id ")
        List<CollectionAttribute> findByCollectionFormId(Long collection_form_id);

}
