package web.ThuThapMau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ThuThapMau.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Các phương thức tùy chỉnh nếu cần
}
