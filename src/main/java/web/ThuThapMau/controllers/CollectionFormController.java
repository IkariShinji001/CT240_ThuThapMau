package web.ThuThapMau.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.dtos.CollectionFormDto;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.entities.CollectionForm;
import web.ThuThapMau.services.CollectionFormService;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionFormController {
    @Autowired
    private CollectionFormService collectionFormService;

    @GetMapping("/api/v1/collection-forms")
    public ResponseEntity<List<CollectionForm>> getCollectionForm() {
        List<CollectionForm> collectionForms = collectionFormService.getAllForm();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(collectionForms);
    }

    @PostMapping("/api/v1/collection-forms")
    public ResponseEntity<CollectionForm> createCollectionForm(@RequestBody CollectionFormDto collectionFormDto) {
        String name = collectionFormDto.getName();
        Long user_id = collectionFormDto.getUser_id();
        Long collection_id = collectionFormDto.getCollection_id();
        List<CollectionAttribute> collectionAttributes = collectionFormDto.getAttributeList();
        CollectionForm collectionForm = collectionFormService.createCollectionForm(user_id, collection_id,
                name, collectionAttributes);
        return ResponseEntity.ok(collectionForm);
    }

    @PatchMapping("/api/v1/collection-forms/{id}")
    public ResponseEntity<CollectionForm> updateCollectionForm(@PathVariable(name = "id") Long collection_form_id, @RequestBody CollectionFormDto collectionFormDto) {
        String name = collectionFormDto.getName();
        Long user_id = collectionFormDto.getUser_id();
        Long collection_id = collectionFormDto.getCollection_id();
        List<CollectionAttribute> collectionAttributes = collectionFormDto.getAttributeList();
        CollectionForm collectionForm = collectionFormService.updateCollectionForm(collection_form_id, user_id, collection_id,
                name, collectionAttributes);
        return ResponseEntity.ok(collectionForm);

    }
}
