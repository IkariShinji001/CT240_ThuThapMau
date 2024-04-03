package web.ThuThapMau.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.dtos.CollectionValueRequestBody;
import web.ThuThapMau.dtos.ListValue;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.services.CollectionValueService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collection-values")
public class CollectionValueController {
    @Autowired
    private CollectionValueService collectionValueService;

    @GetMapping
    public List<CollectionValue> getAll(){
        return collectionValueService.getAllValue();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CollectionValueDto> getFormValue(@PathVariable(name = "id") Long valueId) {
        Optional<CollectionValue> collectionValueOptional = collectionValueService.getCollectionValue(valueId);
        if(collectionValueOptional.isPresent()) {
            CollectionValue collectionValue = collectionValueOptional.get();
            CollectionValueDto collectionValueDto = new CollectionValueDto(
                    collectionValue.getCollection_value_id(),
                    collectionValue.getCollection_value(),
                    collectionValue.getSubmit_time(),
                    collectionValue.getCollection_attribute().getCollection_attribute_id(),
                    collectionValue.getCollection_form().getCollection_form_id(),
                    collectionValue.getUser().getUser_id()
            );
            return new ResponseEntity<>(collectionValueDto, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping
//    public ResponseEntity<List<CollectionValue>> createUser(@RequestBody CollectionValueRequestBody requestBody) {
//        Long user_id = requestBody.getUser_id();
//        Long form_id = requestBody.getCollection_form_id();
//        List<ListValue> valueList = requestBody.getValueList();
//        List<CollectionValue> collectionValues = collectionValueService.createValue(valueList, form_id, user_id);
//        return ResponseEntity.status(201).body(collectionValues);
//    }

    @PostMapping()
    public ResponseEntity<String> createValue(
            @RequestPart("Objs") Map<String, Object> attrs,
            @RequestPart("file") List<MultipartFile> files
    ){

        System.out.println(attrs);
        System.out.println(files);
        return ResponseEntity.status(201).body(attrs.toString() + files);
    }

}