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
import web.ThuThapMau.services.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//        System.out.println("User: " + user);
//        return userService.saveUser(user);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id) {
        User user = userService.getUserById(id).orElse(new User());
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User dataUser){
        userService.updateUser(id, dataUser);
        return ResponseEntity.status(200).body("Cập nhật thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("User with ID " + id + " has been deleted successfully");
    }

    @PostMapping
    public ResponseEntity<User> CreateUser(
                                                 @RequestPart("user_full_name") String user_full_name,
                                                 @RequestPart("user_email") String user_email,
                                                 @RequestPart("user_phone_number") String user_phone_number,
                                                 @RequestPart("user_password") String user_password,
                                                 @RequestPart("file") MultipartFile file) {
        try {
            // Tải ảnh lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String secureUrl = (String) uploadResult.get("secure_url");
            User newUser = new User();
            newUser.setUser_full_name(user_full_name);
            newUser.setUser_email(user_email);
            newUser.setUser_phone_number(user_phone_number);
            newUser.setUser_password(user_password);
            newUser.setUser_image_url(secureUrl);
            return ResponseEntity.ok(newUser);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
