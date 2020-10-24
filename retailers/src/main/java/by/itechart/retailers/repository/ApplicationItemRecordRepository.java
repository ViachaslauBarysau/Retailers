package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationItemRecord;
import by.itechart.retailers.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationItemRecordRepository extends JpaRepository<ApplicationItemRecord, Long> {
    ApplicationItemRecord findApplicationItemRecordByItem(Item item);
}
