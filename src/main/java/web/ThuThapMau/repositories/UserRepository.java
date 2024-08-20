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
    @Query("UPDATE users u SET u.user_full_name = :userName, u.user_email = :userEmail, u.user_phone_number = :userPhoneNumber WHERE u.user_id = :id")
    void updateUserById(Long id, String userName, String userEmail, String userPhoneNumber);


    @Query("SELECT u FROM users u WHERE u.user_email = :user_mail")
    User findByUserEmail(String user_mail);
    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.user_image_url = :image WHERE u.user_id = :user_id")
    void updateUserImage(Long user_id, String image);

    @Modifying
    @Transactional
    @Query("UPDATE users u set u.user_password = :newPassword where  u.user_id = :user_id")
    void updatePasswordByUser_id(Long user_id, String newPassword);

}
