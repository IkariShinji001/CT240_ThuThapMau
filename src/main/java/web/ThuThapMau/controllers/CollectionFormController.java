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
        try{
            List<CollectionForm> collectionForms = collectionFormService.getAllForm();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(collectionForms);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
       }

    @GetMapping("/api/v1/collection-forms/collections/{collection_id}")
    public ResponseEntity<List<CollectionForm>> getCollectionFormByCollectionId( @PathVariable Long collection_id){
        try{
            List<CollectionForm> collectionForms = collectionFormService.getCollectionFormByCollectionId(collection_id);
            return ResponseEntity.status(200).body(collectionForms);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/api/v1/collection-forms/{id}")
    public ResponseEntity<CollectionForm> getCollectionFormById(@PathVariable(name = "id") Long collectionFormId){
        CollectionForm collectionForm = collectionFormService.getForm(collectionFormId);
        return ResponseEntity.status(200).body(collectionForm);
    }
    @PostMapping("/api/v1/collection-forms")
    public ResponseEntity<CollectionForm> createCollectionForm(@RequestBody CollectionFormDto collectionFormDto) {
        try{
            String collection_form_name = collectionFormDto.getCollection_form_name();
            Long user_id = collectionFormDto.getUser_id();
            Long collection_id = collectionFormDto.getCollection_id();
            List<CollectionAttribute> collectionAttributes = collectionFormDto.getAttributeList();
            CollectionForm collectionForm = collectionFormService.createCollectionForm(user_id, collection_id,
                    collection_form_name, collectionAttributes);
            return ResponseEntity.ok(collectionForm);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }

    }

    @PatchMapping("/api/v1/collection-forms/{id}")
    public ResponseEntity<CollectionForm> updateCollectionForm(@PathVariable(name = "id") Long collection_form_id, @RequestBody CollectionFormDto collectionFormDto) {
        try{
            String name = collectionFormDto.getCollection_form_name();
            Long user_id = collectionFormDto.getUser_id();
            Long collection_id = collectionFormDto.getCollection_id();
            List<CollectionAttribute> collectionAttributes = collectionFormDto.getAttributeList();
            CollectionForm collectionForm = collectionFormService.updateCollectionForm(collection_form_id, user_id, collection_id,
                    name, collectionAttributes);
            return ResponseEntity.ok(collectionForm);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}
