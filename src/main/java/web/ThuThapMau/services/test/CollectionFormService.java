package web.ThuThapMau.services.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.repositories.test.CollectionFormRepository;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionFormService {
    @Autowired
    private CollectionFormRepository collectionFormRepository;

    public CollectionForm getForm(Long id){
        return collectionFormRepository.findById(id).orElse(new CollectionForm());
    }
}
