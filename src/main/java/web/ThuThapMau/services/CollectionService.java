package web.ThuThapMau.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.entities.Collection;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.CollectionRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class CollectionService {


    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    public List<Collection> getAllCollection() {
        return collectionRepository.findAll();
    }

    public Collection createCollection(String collection_name,
                                       String collection_start,
                                       String collection_end,
                                       String collection_description,
                                       String project_id,
                                       String user_id,
                                       MultipartFile file) {
        BlockingQueue<String> sharedSecureUrlQueue = new ArrayBlockingQueue<>(1);

        List<String> secureUrlList = new ArrayList<>();
        Thread uploadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Luong Phu");
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String secureUrl = (String) uploadResult.get("secure_url");
                    sharedSecureUrlQueue.put(secureUrl);
                    secureUrlList.add(secureUrl);
                } catch (IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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

            Project project  = projectService.getProjectByProjectId(Long.parseLong(project_id));

            newCollection.setUser(user);
            newCollection.setProject(project);
            newCollection.setCollection_name(collection_name);
            newCollection.setCollection_description(collection_description);
            newCollection.setCollection_start(start);
            newCollection.setCollection_end(end);
            System.out.println(newCollection);

            String secureUrl = sharedSecureUrlQueue.take();
            newCollection.setCollection_image_url(secureUrl);
            collectionRepository.save(newCollection);
            return newCollection;
        } catch ( InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public Optional<Collection> getCollectionById(Long collection_id) {
        return collectionRepository.findById(collection_id);
    }

    public List<Collection> getCollectionsByProjectId(Long project_id){
        return collectionRepository.findCollectionsByProjectId(project_id);
    }

    public Collection updateCollectionById(Long collectionId, Collection payload) {
        String collectionName = payload != null ? payload.getCollection_name() : null;
        String collectionDescription = payload != null ? payload.getCollection_description() : null;
        Date collectionStart = payload != null ? payload.getCollection_start() : null;
        Date collectionEnd = payload != null ? payload.getCollection_end() : null;

        collectionRepository.updateCollectionById(collectionId, collectionName, collectionStart, collectionEnd, collectionDescription);

        Collection updatedCollection = this.getCollectionById(collectionId).get();

        return updatedCollection;
    }



}
