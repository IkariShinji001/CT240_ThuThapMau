package web.ThuThapMau.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.CollectionValue;

import java.util.List;

public interface CollectionValueRepository extends JpaRepository<CollectionValue, Long> {

    @Transactional
    @Query("SELECT MAX(cv.submit_time) FROM CollectionValue cv" +
            " WHERE cv.collection_form.collection_form_id = :form_id " +
            "AND cv.user.user_id = :user_id ")
    Long findMaxSubmit(Long form_id, Long user_id);

    @Query("SELECT cv FROM CollectionValue cv WHERE cv.collection_form.collection_form_id = :collection_form_id")
    List<CollectionValue> findAllValueByFormId(Long collection_form_id);
}
