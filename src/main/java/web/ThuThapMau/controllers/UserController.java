package web.ThuThapMau.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.Middlewares.JwtInterceptor;
import web.ThuThapMau.Util.EmailService;
import web.ThuThapMau.Util.JwtTokenProvider;
import web.ThuThapMau.dtos.EmailDto;
import web.ThuThapMau.dtos.PasswordDto;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired 
    private JwtInterceptor jwtInterceptor;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletResponse response, @CookieValue(name = "jwtToken", required = false) String jwtToken) {
        if (jwtToken != null) {
            Cookie cookie = new Cookie("jwtToken", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
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

    @GetMapping("/verify")
    public ResponseEntity<Boolean> VerifyUser(HttpServletRequest request){
        String jwt = jwtInterceptor.getJwtFromRequest(request);
        try{
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                return  ResponseEntity.status(200).body(true);
            }else {
                return  ResponseEntity.status(400).body(false);
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body(false);
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

    @PatchMapping("/{user_id}/image")
    public ResponseEntity<Optional<User>> updateUserImage(@PathVariable Long user_id,
                                                          @RequestPart("file") MultipartFile file){
        Optional<User> updated = userService.updateUserImage(user_id, file);
        System.out.println(updated);
        return ResponseEntity.status(200).body(updated);
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


}
