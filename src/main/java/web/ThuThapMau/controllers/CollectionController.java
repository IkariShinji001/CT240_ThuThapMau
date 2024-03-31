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
                                                       @RequestPart("project_id") String project_id,
                                                       @RequestPart("user_id") String user_id,
                                                       @RequestPart("file") MultipartFile file) {
        // BlockingQueue để chia sẻ secureUrl giữa các luồng
        BlockingQueue<String> sharedSecureUrlQueue = new ArrayBlockingQueue<>(1);

        // Tạo luồng để tải ảnh lên Cloudinary
        Thread uploadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Luong Phu");
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String secureUrl = (String) uploadResult.get("secure_url");
                    sharedSecureUrlQueue.put(secureUrl);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        uploadThread.start();
        try {
            System.out.println("Luong chinh");
            Collection newCollection = new Collection();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(collection_start);
            Date end = dateFormat.parse(collection_end);
            User user = userService.getUserById(Long.parseLong(user_id)).get();
            user.setUser_id(Long.parseLong(user_id));
            Project project = projectService.getProjectByProjectId(Long.parseLong(project_id));
            project.setProject_id(Long.parseLong(user_id));
            newCollection.setUser(user);
            newCollection.setProject(project);
            newCollection.setCollection_name(collection_name);
            newCollection.setCollection_description(collection_description);
            newCollection.setCollection_end(end);
            newCollection.setCollection_start(start);
            System.out.println(newCollection);
            String secureUrl = sharedSecureUrlQueue.take();
            newCollection.setCollection_image_url(secureUrl);
            collectionService.createCollection(newCollection);
            return ResponseEntity.ok(newCollection);
        } catch (InterruptedException | ParseException e) {
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
