package web.ThuThapMau.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.user_email = :userEmail AND u.user_password = :password")
    User login(String userEmail, String password);

    @Modifying  // Optional for UPDATE queries
    @Transactional
    @Query("UPDATE users u SET u.user_full_name = :userName, u.user_email = :userEmail, u.user_phone_number = :userPhoneNumber, u.user_password = :userPassWord WHERE u.user_id = :id")
    void updateUserById(Long id, String userName, String userEmail, String userPhoneNumber, String userPassWord);


    @Query("SELECT u FROM users u WHERE u.user_email = :user_mail")
    User findByUserEmail(String user_mail);
}
