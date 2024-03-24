package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserId(@PathVariable Long id) {
        User user = userService.getUserById(id).orElse(new User());
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println("User: " + user);
        return userService.saveUser(user);
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User existUser = userService.login(user);
        if(existUser != null){
            return ResponseEntity.status(200).body("Đăng nhập thành công");
        } else {
            return ResponseEntity.status(400).body("Đăng nhập thất bại");
        }
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(@RequestBody String userEmail) {
        userService.forgotPassword(userEmail);
    }



}
