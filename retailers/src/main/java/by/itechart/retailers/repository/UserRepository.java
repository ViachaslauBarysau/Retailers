package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByCustomer_Id(Long id);
}
