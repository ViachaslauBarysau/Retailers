package by.itechart.retailers.repository;

import by.itechart.retailers.entity.WriteOffAct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffActRepository extends JpaRepository<WriteOffAct, Long> {
}
