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
    public ResponseEntity<List<CollectionValue>> getAll() {
        try {
            List<CollectionValue> collectionValues = collectionValueService.getAllValue();
            return ResponseEntity.status(200).body(collectionValues);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionValueDto> getFormValue(@PathVariable(name = "id") Long valueId) {
        try {
            Optional<CollectionValue> collectionValueOptional = collectionValueService.getCollectionValue(valueId);
            if (collectionValueOptional.isPresent()) {
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
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);

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
    ) {
        try {
//            System.out.println(attrs);
//            System.out.println(files);
            return ResponseEntity.status(201).body(attrs.toString() + files);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

}