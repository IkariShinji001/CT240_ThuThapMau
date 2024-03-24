package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.ThuThapMau.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.user_email = :userEmail AND u.user_password = :password")
    User login(String userEmail, String password);
}
