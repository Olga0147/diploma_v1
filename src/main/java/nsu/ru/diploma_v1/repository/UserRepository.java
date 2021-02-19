package nsu.ru.diploma_v1.repository;

import nsu.ru.diploma_v1.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
