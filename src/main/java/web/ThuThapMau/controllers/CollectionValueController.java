package web.ThuThapMau.controllers;


import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.dtos.RequestValueDto;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.services.CollectionValueService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collection-values")
public class CollectionValueController {
    @Autowired
    private CollectionValueService collectionValueService;
    @Autowired
    private Cloudinary cloudinary;


    @GetMapping("/{id}")
    public ResponseEntity<List<CollectionValueDto>> getAllFormValueByCollectionFormId(
            @PathVariable(name = "id")Long collection_form_id) {
        try {
            List<CollectionValueDto> valueDtos = new ArrayList<>();
            List<CollectionValue> collectionValues = collectionValueService.getAllValue(collection_form_id);
            for (CollectionValue colValue : collectionValues) {
                CollectionValueDto tmpDto = new CollectionValueDto();
                tmpDto.setCollection_value_id(colValue.getCollection_value_id());
                tmpDto.setCollection_value(colValue.getCollection_value());
                tmpDto.setCollection_attribute_id(colValue.getCollection_attribute().getCollection_attribute_id());
                tmpDto.setCollection_attribute_name(colValue.getCollection_attribute().getCollection_attribute_name());
                tmpDto.setCollection_form_id(colValue.getCollection_form().getCollection_form_id());
                tmpDto.setUser_id(colValue.getUser().getUser_id());
                tmpDto.setUser_name(colValue.getUser().getUser_full_name());
                tmpDto.setSubmit_time(colValue.getSubmit_time());
                tmpDto.setCollection_created_at(colValue.getCollection_created_at());

                valueDtos.add(tmpDto);
            }
            return ResponseEntity.status(200).body(valueDtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CollectionValueDto> getFormValue(@PathVariable(name = "id") Long valueId) {
//        try {
//            Optional<CollectionValue> collectionValueOptional = collectionValueService.getCollectionValue(valueId);
//            if (collectionValueOptional.isPresent()) {
//                CollectionValue collectionValue = collectionValueOptional.get();
//                CollectionValueDto collectionValueDto = new CollectionValueDto(collectionValue.getCollection_value_id(), collectionValue.getCollection_value(), collectionValue.getSubmit_time(), collectionValue.getCollection_attribute().getCollection_attribute_id(), collectionValue.getCollection_form().getCollection_form_id(), collectionValue.getUser().getUser_id());
//                return new ResponseEntity<>(collectionValueDto, HttpStatus.CREATED);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null);
//        }
//
//    }

    @PostMapping
    public ResponseEntity<List<CollectionValueDto>> createValue(@RequestPart("userId") String user_id,
                                              @RequestPart("collectionFormId") String collection_form_id,
                                              @RequestPart("valueDtos") String valueDtosJson,
                                              @RequestPart("lastIdx") String lastIdx,
                                              @RequestPart("files") List<MultipartFile> files) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<RequestValueDto> valueDtos = mapper.readValue(valueDtosJson, new TypeReference<List<RequestValueDto>>() {
        });


        List<CollectionValue> collectionValues =  collectionValueService.createValue(Long.parseLong(user_id), Long.parseLong(collection_form_id), valueDtos, Long.parseLong(lastIdx), files);

        List<CollectionValueDto> collectionValueDtos = new ArrayList<>();
        for (CollectionValue colValue : collectionValues) {
            CollectionValueDto tmpDto = new CollectionValueDto();
            tmpDto.setCollection_value_id(colValue.getCollection_value_id());
            tmpDto.setCollection_value(colValue.getCollection_value());
            tmpDto.setCollection_attribute_id(colValue.getCollection_attribute().getCollection_attribute_id());
            tmpDto.setCollection_attribute_name(colValue.getCollection_attribute().getCollection_attribute_name());
            tmpDto.setCollection_form_id(colValue.getCollection_form().getCollection_form_id());
            tmpDto.setUser_id(colValue.getUser().getUser_id());
            tmpDto.setUser_name(colValue.getUser().getUser_full_name());
            tmpDto.setSubmit_time(colValue.getSubmit_time());
            tmpDto.setCollection_created_at(colValue.getCollection_created_at());

            collectionValueDtos.add(tmpDto);
        }
        return ResponseEntity.status(201).body(collectionValueDtos);
    }


}