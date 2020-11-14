package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.BillIRecordDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BillRecordService {
    BillIRecordDto findById(long billProductRecordId);

    List<BillIRecordDto> findAll(Pageable pageable);

    BillIRecordDto create(BillIRecordDto billIRecordDto);

    BillIRecordDto update(BillIRecordDto billIRecordDto);
}
