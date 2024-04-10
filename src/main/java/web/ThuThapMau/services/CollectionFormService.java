package web.ThuThapMau.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.CollectionAttributeRepository;
import web.ThuThapMau.repositories.CollectionRepository;
import web.ThuThapMau.repositories.UserRepository;
import web.ThuThapMau.repositories.CollectionFormRepository;

import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionFormService {
    @Autowired
    private CollectionFormRepository collectionFormRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollectionRepository collectionRepository;


    @Autowired
    private CollectionAttributeRepository attributeRepository;

    public List<CollectionForm> getAllForm(){
            return collectionFormRepository.findAll();
    }

    public List<CollectionForm> getCollectionFormByCollectionId(Long collection_id){
        return collectionFormRepository.findByCollectionId(collection_id);
    }
    public CollectionForm getForm(Long id){
        return collectionFormRepository.findById(id).orElse(new CollectionForm());
    }

    public CollectionForm createCollectionForm(Long user_id, Long collection_id, String name,
                                               List<CollectionAttribute> listAttributes){

        User tmpUser = userRepository.findById(user_id).orElse(new User());
        Collection tmpCollection = collectionRepository.findById(collection_id).orElse(new Collection());

        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setCollection_form_name(name);
        collectionForm.setUser(tmpUser);
        collectionForm.setCollection(tmpCollection);
        CollectionForm savedCollectionForm =  collectionFormRepository.save(collectionForm);

        listAttributes.forEach(attribute -> {
            attribute.setCollection_form(savedCollectionForm);
            attributeRepository.save(attribute);
        });

        return savedCollectionForm;
    }

    public CollectionForm updateCollectionForm(Long id, Long user_id, Long collection_id, String name, List<CollectionAttribute> listAttributes) {

        // Find the collection form to update
        CollectionForm collectionFormToUpdate = collectionFormRepository.findById(id).orElse(null);

        // Check if collection form exists
        if (collectionFormToUpdate == null) {
            throw new RuntimeException("Collection form not found with id: " + id);
        }

        // Update fields (optional, update only if provided)
        if (name != null) {
            collectionFormToUpdate.setCollection_form_name(name);
        }

        // Update user association (optional)
        if (user_id != null) {
            User updatedUser = userRepository.findById(user_id).orElse(null);
            if (updatedUser == null) {
                throw new RuntimeException("User not found with id: " + user_id);
            }
            collectionFormToUpdate.setUser(updatedUser);
        }

        // Update collection association (optional)
        if (collection_id != null) {
            Collection updatedCollection = collectionRepository.findById(collection_id).orElse(null);
            if (updatedCollection == null) {
                throw new RuntimeException("Collection not found with id: " + collection_id);
            }
            collectionFormToUpdate.setCollection(updatedCollection);
        }

        // Update existing attributes (optional)
        List<CollectionAttribute> existingAttributes = attributeRepository.findByCollectionFormId(id);
        attributeRepository.deleteAll(existingAttributes);  // Delete existing attributes

        // Save updated collection form and create new attributes
        CollectionForm savedCollectionForm = collectionFormRepository.save(collectionFormToUpdate);
        listAttributes.forEach(attribute -> {
            attribute.setCollection_form(savedCollectionForm);
            attributeRepository.save(attribute);
        });

        return savedCollectionForm;
    }
}
