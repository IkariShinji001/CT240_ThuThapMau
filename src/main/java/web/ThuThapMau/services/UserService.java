package web.ThuThapMau.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.Util.JwtTokenProvider;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean login(User user, HttpServletResponse response){
        String userEmail = user.getUser_email();
        String userPassword = user.getUser_password();
        System.out.println(userEmail + userPassword);
        User existedUser = userRepository.login(userEmail, userPassword);
        System.out.println(existedUser);
        if(existedUser != null){
            String jwtToken = JwtTokenProvider.createJwt(existedUser);
            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/"); // Set cookie path
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

}
