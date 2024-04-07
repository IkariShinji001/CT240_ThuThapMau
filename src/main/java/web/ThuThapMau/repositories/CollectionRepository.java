package web.ThuThapMau.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.Collection;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
//    Collection getReferenceById(Long id);

    @Override
    Optional<Collection> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Collection c SET c.collection_name = :collection_name, c.collection_start = :collection_start , c.collection_end = :collection_end, c.collection_description = :collection_description WHERE c.collection_id = :collection_id ")
    void updateCollectionById(Long collection_id, String collection_name, Date collection_start, Date collection_end,String collection_description);

    @Query("SELECT c FROM Collection c WHERE c.project.project_id = :project_id")
    List<Collection> findCollectionsByProjectId(Long project_id);
}