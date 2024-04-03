package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import web.ThuThapMau.dtos.AttributeDto;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.services.CollectionAttributeService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CollectionAttributeController {
    private CollectionAttributeService collectionAttributeService;

    @Autowired
    public CollectionAttributeController(CollectionAttributeService collectionAttributeService) {
        this.collectionAttributeService = collectionAttributeService;
    }

    @GetMapping("/api/v1/collection-attributes")
    public ResponseEntity<List<AttributeDto>> getAllAttributes() {
        try {
            List<CollectionAttribute> collectionAttributes = collectionAttributeService.getAllAttributes();
            List<AttributeDto> attributeDtos = new ArrayList<>();

            for (CollectionAttribute attribute : collectionAttributes) {
                AttributeDto attributeDto = new AttributeDto();
                attributeDto.setCollection_attribute_id(attribute.getCollection_attribute_id());
                attributeDto.setCollection_attribute_name(attribute.getCollection_attribute_name());
                attributeDto.setCollection_form(attribute.getCollection_form().getCollection_form_id()); // Lấy ID của CollectionForm

                attributeDtos.add(attributeDto);
            }
            return ResponseEntity.status(200).body(attributeDtos);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/api/v1/collection-attributes/{form_id}")
    public ResponseEntity<List<AttributeDto>> getAllAttributeByFormId(@PathVariable Long form_id) {
        List<CollectionAttribute> collectionAttributes = collectionAttributeService.getAllAttributesByFormId(form_id);
        List<AttributeDto> attributeDtos = new ArrayList<>();

        for (CollectionAttribute attribute : collectionAttributes) {
            AttributeDto attributeDto = new AttributeDto();
            attributeDto.setCollection_attribute_id(attribute.getCollection_attribute_id());
            attributeDto.setCollection_attribute_name(attribute.getCollection_attribute_name());
            attributeDto.setCollection_form(attribute.getCollection_form().getCollection_form_id()); // Lấy ID của CollectionForm

            attributeDtos.add(attributeDto);
        }
        return ResponseEntity.status(200).body(attributeDtos);
    }

}
