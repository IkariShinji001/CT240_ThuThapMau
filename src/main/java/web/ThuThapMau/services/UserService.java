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
        System.out.println(newUser.getUser_image_url());
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
