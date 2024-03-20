package web.ThuThapMau.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.dtos.CollectionValueDto;
import web.ThuThapMau.entities.CollectionValue;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.services.CollectionValueService;

import java.util.List;

@RestController
public class CollectionValueController {
    @Autowired
    private CollectionValueService collectionValueService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/api/v1/collection-value/{id}")
    public ResponseEntity<CollectionValueDto> getFormValue(@PathVariable(name = "id") Long valueId) {
        CollectionValue collectionValue = collectionValueService.getFormValue(valueId);
        CollectionValueDto collectionValueDto = modelMapper.map(collectionValue, CollectionValueDto.class);
        return new ResponseEntity<>(collectionValueDto, HttpStatus.CREATED);
    }

//    @GetMapping("/api/v1/collection-value/max/{form_id}/{user_id}")
    public Long getMax(@PathVariable(name = "form_id") Long form_id,
                       @PathVariable Long user_id) {
        Long maxSubmitTime = collectionValueService.findMaxSubmitTimeById(form_id, user_id);
        if(maxSubmitTime == null){
            maxSubmitTime = 0L;
        }
        return maxSubmitTime;
    }


//    @PostMapping("/api/v1/collection-value/{form_id}/{user_id}")
//    public ResponseEntity<List<CollectionValueDto>> createUser(
//                                            @RequestBody List<CollectionValueDto> listValueDto,
//                                            @PathVariable("form_id") Long form_id,
//                                            @PathVariable("user_id") Long user_id) {
//
//        List<CollectionValueDto> collectionValues = collectionValueService.createValue(listValueDto, form_id, user_id);
//        return ResponseEntity.status(201).body(collectionValues);
//    }

}