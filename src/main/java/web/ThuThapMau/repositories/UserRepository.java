package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.user_full_name = :userName, u.user_email = :userEmail, u.user_phone_number = :userPhoneNumber,u.user_password = :userPassword WHERE u.user_id = :id")
    void updateUserById(Long id, String userName, String userEmail, String userPhoneNumber, String userPassword);


    @Query("SELECT u FROM users u WHERE u.user_email =:userEmail AND u.user_password =:userPassword ")
    User login(String userEmail, String userPassword);

    boolean existByUserEmail(String userEmail);

}
