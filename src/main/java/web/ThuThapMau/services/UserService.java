package web.ThuThapMau.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.ThuThapMau.Util.JwtTokenProvider;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    public Optional<User> updateUserImage(Long user_id, MultipartFile file){
        BlockingQueue<String> sharedSecureUrlQueue = new ArrayBlockingQueue<>(1);

        Thread uploadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Luong Phu");
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String secureUrl = (String) uploadResult.get("secure_url");
                    sharedSecureUrlQueue.put(secureUrl);
                } catch (IOException e){
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        uploadThread.start();
        try {
            String image = sharedSecureUrlQueue.take();
            userRepository.updateUserImage(user_id, image);
            Optional<User> updated = Optional.of(userRepository.findById(user_id).get());
            return updated;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String user_mail){
        return userRepository.findByUserEmail(user_mail);
    }
    public User createUser(User newUser){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(newUser.getUser_password());
        newUser.setUser_password(encodedPassword);
        return userRepository.save(newUser);
    }
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void updateUser(Long id, User dataUser){
        String userName = dataUser != null ? dataUser.getUser_full_name() : null;
        String userEmail = dataUser != null ? dataUser.getUser_email() : null;
        String userPhoneNumber = dataUser != null ? dataUser.getUser_phone_number() : null;
        userRepository.updateUserById(id, userName, userEmail, userPhoneNumber);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User login(User user, HttpServletResponse response){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userEmail = user.getUser_email();
        String userPassword = user.getUser_password();
        User existedUser = userRepository.findByUserEmail(userEmail);
        if(existedUser != null){
            boolean passwordMatches = encoder.matches(userPassword, existedUser.getUser_password());
            if(!passwordMatches){
                return null;
            }
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
    public void  updatePassword(Long user_id, String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(newPassword);
        userRepository.updatePasswordByUser_id(user_id, hashedPassword);
    }

}
