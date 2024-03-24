package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.CollectionDTO;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.services.CollectionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/collections")

public class CollectionController {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public List<Collection> getAllCollection() {return collectionService.getAllCollection();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<List<CollectionDTO>> getCollectionsByProjectId(@PathVariable(name = "id") Long project_id){
        List<Collection> data =  collectionService.getCollectionsByProjectId(project_id);
        List<CollectionDTO> listCollection = new ArrayList<>();
        data.forEach((collection -> {
            System.out.println(collection);
            CollectionDTO current = new CollectionDTO();
            current.setCollection_id(collection.getCollection_id());
            current.setCollection_name(collection.getCollection_name());
            current.setCollection_description(collection.getCollection_description());
            current.setCollection_end(collection.getCollection_end());
            current.setCollection_start(collection.getCollection_start());
            current.setCollection_image_url(collection.getCollection_image_url());
            current.setCollection_created_at(collection.getCollection_created_at());
            listCollection.add(current);
        }));
        return ResponseEntity.status(200).body(listCollection);
    }

    @PostMapping
    public ResponseEntity<Collection> uploadData(@RequestPart("description") Collection collection,
                                             @RequestPart("file") MultipartFile file) {
        try {
            // Tải ảnh lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = (String) uploadResult.get("secure_url");
            collection.setCollection_image_url(secureUrl);
            Collection newCollection = collectionService.createCollection(collection);
            return ResponseEntity.ok(newCollection);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
