package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.services.CollectionService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.SimpleDateFormat;

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

    @GetMapping("/projects/{project_id}")
    public ResponseEntity<List<Collection>> getCollectionsByProjectId(@PathVariable Long project_id){
        List<Collection> collections = collectionService.getCollectionsByProjectId(project_id);
        System.out.println(collections);
        return ResponseEntity.status(200).body(collections);
    }
    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestParam("collection_name") String collection_name,
                                                 @RequestPart("collection_start") String collection_start,
                                                 @RequestPart("collection_end") String collection_end,
                                                 @RequestPart("collection_description") String collection_description,
                                                 @RequestPart("collection_created_at") String collection_created_at,
                                                 @RequestPart("file") MultipartFile file) {
        try {
            // Tải ảnh lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = (String) uploadResult.get("secure_url");
            Collection newCollection = new Collection();
            newCollection.setCollection_image_url(secureUrl);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(collection_start);
            Date end = dateFormat.parse(collection_end);
            Date create =dateFormat.parse(collection_created_at);

            newCollection.setCollection_name(collection_name);
            newCollection.setCollection_created_at(create);
            newCollection.setCollection_description(collection_description);
            newCollection.setCollection_end(end);
            newCollection.setCollection_start(start);
            return ResponseEntity.ok(newCollection);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (ParseException e) {
            throw new RuntimeException(e);
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
