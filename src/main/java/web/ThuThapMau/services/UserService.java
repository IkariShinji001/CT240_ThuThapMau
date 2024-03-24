package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ThuThapMau.entities.User;
import web.ThuThapMau.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void updateUser(Long id, User dataUser){
        String userName = dataUser != null ? dataUser.getUser_full_name() : null;
        String userEmail = dataUser != null ? dataUser.getUser_email() : null;
        String userPhoneNumber = dataUser != null ? dataUser.getUser_phone_number() : null;
        String userPassword = dataUser != null ? dataUser.getUser_password() : null;
        userRepository.updateUserById(id, userName, userEmail, userPhoneNumber, userPassword);
    }

    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }

    public User login(User user){
        String userEmail = user != null ? user.getUser_email() :null;
        String userPassword = user != null ? user.getUser_password() :null;
        return userRepository.login(userEmail, userPassword);
    }


}
