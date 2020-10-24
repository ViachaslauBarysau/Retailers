package by.itechart.retailers.repository;

import by.itechart.retailers.entity.BillItemRecord;
import by.itechart.retailers.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRecordRepository extends JpaRepository<BillItemRecord, Long> {
    BillItemRecord findBillItemRecordByItem(Item item);
}
