package by.itechart.retailers.repository;

import by.itechart.retailers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
