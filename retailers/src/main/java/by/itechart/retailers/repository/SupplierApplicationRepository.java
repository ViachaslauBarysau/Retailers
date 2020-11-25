package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.entity.InnerApplication;
import by.itechart.retailers.entity.SupplierApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierApplicationRepository extends JpaRepository<SupplierApplication, Long> {

    List<SupplierApplication> findAllByRecordsListIn(List<ApplicationRecord> applicationRecords);

    Optional<SupplierApplication> findByApplicationNumber(Integer applicationNumber);

    Page<SupplierApplication> findAllByDestinationLocation_Id(Pageable pageable, Long locationId );
}
