package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ThuThapMau.entities.Collection;

import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
//    Collection getReferenceById(Long id);

}