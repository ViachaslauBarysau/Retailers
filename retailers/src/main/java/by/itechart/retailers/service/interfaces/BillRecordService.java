package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.BillIRecordDto;

import java.util.List;

public interface BillRecordService {
    BillIRecordDto findById(long billProductRecordId);

    List<BillIRecordDto> findAll();

    BillIRecordDto create(BillIRecordDto billIRecordDto);

    BillIRecordDto update(BillIRecordDto billIRecordDto);
}
