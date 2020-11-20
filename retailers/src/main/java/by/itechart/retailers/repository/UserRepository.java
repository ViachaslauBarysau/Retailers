package by.itechart.retailers.repository;

import by.itechart.retailers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    Page<User> findAllByCustomer_Id(Pageable pageable, Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
