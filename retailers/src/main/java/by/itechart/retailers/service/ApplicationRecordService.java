package by.itechart.retailers.service;

import by.itechart.retailers.dto.ApplicationRecordDto;

import java.util.List;

public interface ApplicationRecordService {

    ApplicationRecordDto findById(long applicationProductRecordId);

    List<ApplicationRecordDto> findAll();

    ApplicationRecordDto create(ApplicationRecordDto applicationRecordDto);

    ApplicationRecordDto update(ApplicationRecordDto applicationRecordDto);

}
