package web.ThuThapMau.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.dtos.RequestValueDto;
import web.ThuThapMau.entities.*;
import web.ThuThapMau.repositories.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Data
@Service
public class CollectionValueService {
    private CollectionValueRepository collectionValueRepository;
    private UserRepository userRepository;
    private CollectionAttributeRepository attributeRepository;
    private CollectionFormRepository collectionFormRepo;
    private Cloudinary cloudinary;

    @Autowired
    public CollectionValueService(CollectionValueRepository collectionValueRepository, UserRepository userRepository, CollectionAttributeRepository attributeRepository, CollectionFormRepository collectionFormRepo, Cloudinary cloudinary) {
        this.collectionValueRepository = collectionValueRepository;
        this.userRepository = userRepository;
        this.attributeRepository = attributeRepository;
        this.collectionFormRepo = collectionFormRepo;
        this.cloudinary = cloudinary;
    }

    public List<CollectionValue> getAllValue(Long collection_form_id) {
        return collectionValueRepository.findAllValueByFormId(collection_form_id);
    }

    public Optional<CollectionValue> getCollectionValue(Long valueId) {
        return collectionValueRepository.findById(valueId);
    }

    public Long findMaxSubmitTimeById(Long userId, Long formId) {
        Long max = collectionValueRepository.findMaxSubmit(formId, userId);
        if (max == null) max = 0L;
        return max;
    }


    public List<CollectionValue> createValue(Long user_id, Long collection_form_id, List<RequestValueDto> valueDtos, Long lastIdx, List<MultipartFile> files) {
        BlockingQueue<String> sharedSecureUrlQueue = new ArrayBlockingQueue<>(100);

        List<Thread> uploadThreads = new ArrayList<>();
        for (MultipartFile file : files) {
            Thread uploadThread = new Thread(() -> {
                try {
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String secureUrl = (String) uploadResult.get("secure_url");
                    sharedSecureUrlQueue.put(secureUrl);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            uploadThreads.add(uploadThread);
            uploadThread.start();
        }

        for (Thread uploadThread : uploadThreads) {
            try {
                uploadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            User user = userRepository.findById(user_id).orElse(new User());
            CollectionForm collectionForm = collectionFormRepo.findById(collection_form_id).orElse(new CollectionForm());
            Long maxSubmitTime = findMaxSubmitTimeById(user_id, collection_form_id) + 1;
            List<CollectionValue> collectionValueList = new ArrayList<>();

            for (RequestValueDto valueDto : valueDtos) {
                CollectionAttribute tmpAttr = attributeRepository.findById(valueDto.getCollection_attribute_id()).orElse(new CollectionAttribute());
                CollectionValue collectionValue = new CollectionValue();
                collectionValue.setUser(user);
                collectionValue.setCollection_form(collectionForm);
                collectionValue.setCollection_attribute(tmpAttr);
                collectionValue.setSubmit_time(maxSubmitTime);
                collectionValue.setCollection_value(valueDto.getCollection_value());

                collectionValueList.add(collectionValue);
            }

            for (int i = 0; i < files.size(); i++) {
                CollectionAttribute tmpAttr = attributeRepository.findById(lastIdx).orElse(new CollectionAttribute());
                CollectionValue tmpCollectionVal = new CollectionValue();
                tmpCollectionVal.setUser(user);
                tmpCollectionVal.setCollection_form(collectionForm);
                tmpCollectionVal.setCollection_attribute(tmpAttr);
                tmpCollectionVal.setSubmit_time(maxSubmitTime);
                tmpCollectionVal.setCollection_value(sharedSecureUrlQueue.poll());

                collectionValueList.add(tmpCollectionVal);
            }

            List<CollectionValue> createdCollectionValues = collectionValueRepository.saveAll(collectionValueList);
            return createdCollectionValues;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


