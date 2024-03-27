package web.ThuThapMau.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import web.ThuThapMau.Util.JwtTokenProvider;
import web.ThuThapMau.entities.Project;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        validateUserFields(user);
        try {
            return userRepository.save(user);
        }catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }
    private void validateUserFields(User user) {
        if (user.getUser_full_name() == null || user.getUser_full_name().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full Name cannot be empty");
        }
        if (user.getUser_email() == null || user.getUser_email().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be empty");
        }
        if (user.getUser_phone_number() == null || user.getUser_phone_number().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number cannot be empty");
        }
        if (user.getUser_password() == null || user.getUser_password().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be empty");
        }
    }


    public User createUser(User newUser){
        return userRepository.save(newUser);
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void updateUser(Long id, User dataUser){
        String userName = dataUser != null ? dataUser.getUser_full_name() : null;
        String userEmail = dataUser != null ? dataUser.getUser_email() : null;
        String userPhoneNumber = dataUser != null ? dataUser.getUser_phone_number() : null;
        String userPassWord = dataUser != null ? dataUser.getUser_password() : null;
        userRepository.updateUserById(id, userName, userEmail, userPhoneNumber, userPassWord);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User login(User user, HttpServletResponse response){
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
            return existedUser;
        }
        return null;
    }

}
