package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.services.CollectionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collections")

public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping
    public List<Collection> getAllCollection() {return collectionService.getAllCollection();
    }

    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestBody Collection newCollection){
        Collection collection = collectionService.createCollection(newCollection);
        return ResponseEntity.status(201).body(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Collection>> getCollectionById(@PathVariable Long id) {
        Optional<Collection> collection = collectionService.getCollectionById(id);
        return ResponseEntity.status(201).body(collection);
    }
    @PatchMapping("/{id}")
    public ResponseEntity updateCollectionByID(@PathVariable Long id, @RequestBody Collection payload){
        collectionService.updateCollectionById(id, payload);
        return ResponseEntity.status(200).body("Cập nhật thành công");
    }


}
