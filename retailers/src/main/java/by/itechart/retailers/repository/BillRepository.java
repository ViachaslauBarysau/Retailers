package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNumber(Integer billNumber);

    Page<Bill> findAllByLocationIn(Pageable pageable, List<Location> locations);//доставать по листу Locations в findAll

}
