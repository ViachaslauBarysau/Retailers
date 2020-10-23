package by.itechart.retailers.service;

import by.itechart.retailers.dto.WriteOffActItemRecordDto;

import java.util.List;

public interface WriteOffActItemRecordService {
    WriteOffActItemRecordDto findById(long writeOffActItemRecordId);

    List<WriteOffActItemRecordDto> findAll();

    WriteOffActItemRecordDto create(WriteOffActItemRecordDto writeOffActItemRecordDto);

    WriteOffActItemRecordDto update(WriteOffActItemRecordDto writeOffActItemRecordDto);
}
