package by.itechart.retailers.repository;

import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import by.itechart.retailers.entity.DeletedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Page<Location> findAllByCustomer_IdAndStatus(Pageable pageable, Long customerId, DeletedStatus status);

    List<Location> findAllByCustomer_IdAndLocationTypeAndStatus(Long customer_id, LocationType locationType, DeletedStatus status);

    Optional<Location> findByIdentifier(String identifier);

    List<Location> findAllByCustomer_Id(Long customerId);

}
