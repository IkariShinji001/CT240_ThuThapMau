package web.ThuThapMau.controllers;

import com.cloudinary.Cloudinary;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.Util.EmailService;
import web.ThuThapMau.dtos.EmailDto;
import web.ThuThapMau.dtos.PasswordDto;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail/forget-password")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) {
        try {
            System.out.println(emailDto);
            User user = userService.getUserByEmail(emailDto.getUser_email());
            long user_id =  userService.getUserByEmail(emailDto.getUser_email()).getUser_id() ;
            emailService.sendEmail(emailDto.getUser_email(), user_id);
            return ResponseEntity.status(200).body("Gui mail thanh cong");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try{
            List<User> users = userService.getAllUsers();
            return ResponseEntity.status(200).body(users);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id).orElse(new User());
            return ResponseEntity.status(201).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/emails/{user_mail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String user_mail) {
        try {
            User user = userService.getUserByEmail(user_mail);
            if (user != null) {
                return ResponseEntity.status(200).body(user);
            }
            return ResponseEntity.status(400).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User dataUser) {
        try {
            userService.updateUser(id, dataUser);
            return ResponseEntity.status(200).body("Cập nhật thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @PatchMapping("/reset-password")
    public  ResponseEntity<String> updatePassword(@RequestParam Long user_id, @RequestBody PasswordDto passwordDto){
        try{
//
            userService.updatePassword(user_id, passwordDto.getNewPassword());
            return  ResponseEntity.status(200).body("Cập nhật thành công");
        } catch (Exception e){
            return  ResponseEntity.status(200).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(200).body("User with ID " + id + " has been deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<User> CreateUser(@RequestBody User payload){
        try{
            payload.setUser_image_url("https://as2.ftcdn.net/v2/jpg/04/10/43/77/1000_F_410437733_hdq4Q3QOH9uwh0mcqAhRFzOKfrCR24Ta.jpg");
            User newuser =  userService.createUser(payload);
            return ResponseEntity.status(200).body(newuser);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }

    }
}
