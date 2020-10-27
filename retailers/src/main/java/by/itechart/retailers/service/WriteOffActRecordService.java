package by.itechart.retailers.service;

import by.itechart.retailers.dto.WriteOffActRecordDto;

import java.util.List;

public interface WriteOffActRecordService {
    WriteOffActRecordDto findById(long writeOffActProductRecordId);

    List<WriteOffActRecordDto> findAll();

    WriteOffActRecordDto create(WriteOffActRecordDto writeOffActRecordDto);

    WriteOffActRecordDto update(WriteOffActRecordDto writeOffActRecordDto);
}
