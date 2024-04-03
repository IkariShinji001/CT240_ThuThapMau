package web.ThuThapMau.controllers;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.dtos.TestValueDto;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.services.CollectionValueService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collection-values")
public class CollectionValueController {
    @Autowired
    private CollectionValueService collectionValueService;
    @Autowired
    private Cloudinary cloudinary;


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


    @PostMapping
    public ResponseEntity<String> createValue(
            @RequestPart("userId") String user_id,
            @RequestPart("collectionFormId") String collection_form_id,
            @RequestPart("valueDtos") String valueDtosJson,
            @RequestPart("lastIdx") String lastIdx,
            @RequestPart("files") List<MultipartFile> files
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<TestValueDto> valueDtos = mapper.readValue(valueDtosJson, new TypeReference<List<TestValueDto>>() {});

        collectionValueService.createValue(Long.parseLong(user_id), Long.parseLong(collection_form_id), valueDtos, Long.parseLong(lastIdx), files);
        return ResponseEntity.status(201).body("hukhan");
    }


}