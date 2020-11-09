package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    List<Location> findAllByCustomer_Id(Long id);
}
