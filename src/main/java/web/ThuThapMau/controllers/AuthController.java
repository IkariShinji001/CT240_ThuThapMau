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
@RequestMapping("/public/login")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping()
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
