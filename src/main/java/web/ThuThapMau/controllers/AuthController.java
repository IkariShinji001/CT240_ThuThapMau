package web.ThuThapMau.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.UserService;

@RestController
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        try {
            User existedUser = userService.login(user, response);
            if (existedUser != null) {
                return ResponseEntity.status(200).body(existedUser);
            } else {
                return ResponseEntity.status(400).body(null);
            }
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
