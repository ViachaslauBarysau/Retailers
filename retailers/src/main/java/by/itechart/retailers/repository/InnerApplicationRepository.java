package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.entity.InnerApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InnerApplicationRepository extends JpaRepository<InnerApplication, Long> {
    List<InnerApplication> findAllByRecordsListIn(List<ApplicationRecord> applicationRecords);

    Optional<InnerApplication> findByApplicationNumber(Integer applicationNumber);

    Page<InnerApplication> findAllByDestinationLocation_Id(Pageable pageable, Long locationId);
}
