package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.CollectionForm;

import java.util.List;

public interface CollectionFormRepository extends JpaRepository<CollectionForm, Long> {
    @Query("SELECT clp FROM CollectionForm clp WHERE clp.collection.collection_id = :collection_id")
    List<CollectionForm> findByCollectionId(Long collection_id);
}
