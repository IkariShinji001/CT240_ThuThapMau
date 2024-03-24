package web.ThuThapMau.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.repositories.CollectionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public List<Collection> getCollectionsByProjectId(Long project_id){
        return collectionRepository.findCollectionsByProjectId(project_id);
    }

    public void updateCollectionById(Long collectionId, Collection payload) {
        String collectionName = payload != null ? payload.getCollection_name() : null;
        String collectionDescription = payload != null ? payload.getCollection_description() : null;
        String collectionImageUrl = payload != null ? payload.getCollection_image_url() : null;
        Date collectionStart = payload != null ? payload.getCollection_start() : null;
        Date collectionEnd = payload != null ? payload.getCollection_end() : null;

        collectionRepository.updateCollectionById(collectionId, collectionName, collectionStart, collectionEnd, collectionImageUrl,collectionDescription);
    }


}
