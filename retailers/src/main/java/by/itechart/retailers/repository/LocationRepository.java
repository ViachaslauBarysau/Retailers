package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Customer;
import by.itechart.retailers.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    Page<Location> findAllByCustomer_Id(Pageable pageable,Long id);
}
