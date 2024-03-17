package web.ThuThapMau.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
