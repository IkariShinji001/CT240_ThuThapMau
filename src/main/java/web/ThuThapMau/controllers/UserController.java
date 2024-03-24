package web.ThuThapMau.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.UserService;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> getUserId(@PathVariable Long id) {
        System.out.println("User ID: + id " + id);
        return ResponseEntity.status(201).body(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        System.out.println("User: " + user);
        return userService.saveUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody User user, HttpServletResponse response){
        Boolean isLogin = userService.login(user, response);

        System.out.println(isLogin);
        if(isLogin){
            return ResponseEntity.status(200).body(isLogin);
        }else {
            return ResponseEntity.status(400).body(isLogin);
        }
    }
}
