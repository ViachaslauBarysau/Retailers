package by.itechart.retailers.repository;

import by.itechart.retailers.entity.WriteOffAct;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WriteOffActRepository extends JpaRepository<WriteOffAct, Long> {
    Optional<WriteOffAct> findByWriteOffActNumber(Integer writeOffActNumber);
}
