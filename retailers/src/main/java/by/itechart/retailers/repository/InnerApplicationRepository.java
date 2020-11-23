package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.entity.InnerApplication;
import by.itechart.retailers.entity.SupplierApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InnerApplicationRepository extends JpaRepository<InnerApplication, Long> {
    List<InnerApplication> findAllByRecordsListIn(List<ApplicationRecord> applicationRecords);
}
