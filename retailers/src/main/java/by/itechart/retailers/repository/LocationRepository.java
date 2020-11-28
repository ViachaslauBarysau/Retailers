package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    Page<Location> findAllByCustomer_Id(Pageable pageable, Long customerId);

    List<Location> findAllByCustomer_IdAndLocationType(Long customerId, LocationType type);

    Optional<Location> findByIdentifier(String identifier);

    List<Location> findAllByCustomer_Id(Long customerId);

}
