package web.ThuThapMau.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.dtos.ListValue;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.CollectionAttributeRepository;
import web.ThuThapMau.repositories.CollectionValueRepository;
import web.ThuThapMau.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@Service
public class CollectionValueService {
    @Autowired
    private CollectionValueRepository collectionValueRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CollectionAttributeRepository attributeRepository;

    @Autowired
    private CollectionFormService collectionFormService;



    public List<CollectionValue> getAllValue(){
        return collectionValueRepository.findAll();
    }
    public Optional<CollectionValue> getCollectionValue(Long valueId){
        return collectionValueRepository.findById(valueId);
    }
//
    public Long findMaxSubmitTimeById(Long formId, Long userId){
        Long max = collectionValueRepository.findMaxSubmit(formId, userId);
        if (max == null) max = 0L;
        return max;
    }


    public List<CollectionValue> createValue(List<ListValue> listValue, Long formId, Long userId) {
        User tmpUser = userRepository.findById(userId).orElse(new User());
        CollectionForm tmpCollectionForm = collectionFormService.getForm(formId);
        Long maxSubmitTime = findMaxSubmitTimeById(formId, userId) + 1;

        List<CollectionValue> collectionValueList = new ArrayList<>();
        for (ListValue value : listValue) {
            CollectionAttribute tmpAttr = attributeRepository.findById(value.getCollection_attribute_id())
                    .orElse(new CollectionAttribute());
            CollectionValue tmpCollectionValue = new CollectionValue();
            tmpCollectionValue.setUser(tmpUser);
            tmpCollectionValue.setCollection_form(tmpCollectionForm);
            tmpCollectionValue.setCollection_attribute(tmpAttr);
            tmpCollectionValue.setSubmit_time(maxSubmitTime);
            tmpCollectionValue.setCollection_value(value.getCollection_value());
            collectionValueList.add(tmpCollectionValue);
        }

        return collectionValueRepository.saveAll(collectionValueList);
    }

}


