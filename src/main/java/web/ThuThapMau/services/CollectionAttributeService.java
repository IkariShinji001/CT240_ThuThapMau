package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.repositories.CollectionAttributeRepository;

import java.util.List;

@Service
public class CollectionAttributeService {
    private CollectionAttributeRepository collectionAttributeRepository;

    @Autowired
    public CollectionAttributeService(CollectionAttributeRepository collectionAttributeRepository){
        this.collectionAttributeRepository = collectionAttributeRepository;
    }

    public List<CollectionAttribute> getAllAttributes(){
        return collectionAttributeRepository.findAll();
    }

    public List<CollectionAttribute> getAllAttributesByFormId(Long formId) {
        return collectionAttributeRepository.findByCollectionFormId(formId);
    }
}
