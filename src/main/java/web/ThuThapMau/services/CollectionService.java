package web.ThuThapMau.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.repositories.CollectionRepository;
import  web.ThuThapMau.entities.Collection;

import java.util.*;
@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;
    public List<Collection> getAllCollection() {
        return collectionRepository.findAll();
    }

    public Collection createCollection(Collection newCollection) {
        return collectionRepository.save(newCollection);
    }
    public Optional<Collection> getCollectionById(Long collection_id) {
        return collectionRepository.findById(collection_id);
    }


}
