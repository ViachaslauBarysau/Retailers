package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
