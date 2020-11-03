package by.itechart.retailers.repository;

import by.itechart.retailers.entity.InnerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InnerApplicationRepository extends JpaRepository<InnerApplication, Long> {
}
