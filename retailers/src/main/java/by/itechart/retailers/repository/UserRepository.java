package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Role;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Page<User> findAllByCustomer_Id(Pageable pageable, Long id);

    List<User> findAllByLocation_IdAndUserStatus(Long locationId, Status status);

    List<User> findAllByEmail(String email);

    List<User> findAllByLogin(String login);

    List<User> findAllByBirthdayAndUserStatus(LocalDate date, Status status);

    List<User> findAllByUserRoleAndUserStatus(Role role, Status status);

}
