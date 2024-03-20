package web.ThuThapMau.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import web.ThuThapMau.dtos.AttributeDto;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.CollectionValueRepository;
import web.ThuThapMau.repositories.UserRepository;
import web.ThuThapMau.services.test.CollectionFormService;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Service
public class CollectionValueService {
    @Autowired
    private CollectionValueRepository collectionValueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CollectionFormService collectionFormService;

    public CollectionValue getFormValue(Long valueId){
        return collectionValueRepository.findById(valueId).orElse(new CollectionValue());
    }
//
    public Long findMaxSubmitTimeById(Long formId, Long userId){
        return  collectionValueRepository.findMaxSubmit(formId, userId);
    }


//    public List<CollectionValue> createValue(List<CollectionValue> listValue, Long formId, Long userId) {
//        User tmpUser = userRepository.findById(userId).orElse(new User());
//        CollectionForm newCollectionForm = collectionFormService.getForm(formId);
//
//        Long maxSubmitTime = findMaxSubmitTimeById(formId, userId) + 1;
//        User newUser = new User();
//        newUser.setUser_id(userId);
//        newCollectionForm.setForm_id(formId);
//
////        System.out.println(listValue);
////        listValue.forEach(row ->{
////            row.setUser_id(userId);
////            row.setForm_id(formId);
////            row.setForm_submit_time(maxSubmitTime);
////        });
//
//        System.out.println(listValue);
//
//        List<CollectionValue> collectionValueDtos = collectionValueRepository.saveAll(listValue)
//                .stream().map(attribute -> modelMapper.map(attribute, CollectionValue.class)).toList();
//        System.out.println(collectionValueDtos);
//
//        return collectionValueDtos;
//    }

    // teestt
//    public List<CollectionValue> createValue(List<CollectionValue> listValue, Long formId, Long userId) {
//        listValue.forEach(row ->{
//            row.setUser();
//        })
//    }
}


