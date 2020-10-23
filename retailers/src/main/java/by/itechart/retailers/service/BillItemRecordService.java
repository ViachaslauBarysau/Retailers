package by.itechart.retailers.service;

import by.itechart.retailers.dto.BillItemRecordDto;

import java.util.List;

public interface BillItemRecordService {
    BillItemRecordDto findById(long billItemRecordId);

    List<BillItemRecordDto> findAll();

    BillItemRecordDto create(BillItemRecordDto billItemRecordDto);

    BillItemRecordDto update(BillItemRecordDto billItemRecordDto);
}
