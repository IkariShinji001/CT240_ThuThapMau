package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.CollectionService;
import web.ThuThapMau.services.ProjectService;
import web.ThuThapMau.services.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/api/v1/collections")

public class CollectionController {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

    @GetMapping
    public List<Collection> getAllCollection() {
        return collectionService.getAllCollection();
    }

    @GetMapping("/projects/{project_id}")
    public ResponseEntity<List<Collection>> getCollectionsByProjectId(@PathVariable Long project_id) {
        List<Collection> collections = collectionService.getCollectionsByProjectId(project_id);
        System.out.println(collections);
        return ResponseEntity.status(200).body(collections);
    }

    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestParam("collection_name") String collection_name,
                                                       @RequestPart("collection_start") String collection_start,
                                                       @RequestPart("collection_end") String collection_end,
                                                       @RequestPart("collection_description") String collection_description,
                                                       @RequestPart("project_id") String project_id,
                                                       @RequestPart("user_id") String user_id,
                                                       @RequestPart("file") MultipartFile file) {
        try {
            Collection collection = collectionService.createCollection(collection_name, collection_start, collection_end, collection_description, project_id, user_id, file);
            return ResponseEntity.status(200).body(collection);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Collection>> getCollectionById(@PathVariable Long id) {
        try {
            Optional<Collection> collection = collectionService.getCollectionById(id);
            return ResponseEntity.status(201).body(collection);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Collection> updateCollectionByID(@PathVariable Long id, @RequestBody Collection payload) {
        try {
            Collection updatedCollection = collectionService.updateCollectionById(id, payload);
            return ResponseEntity.status(200).body(updatedCollection);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
