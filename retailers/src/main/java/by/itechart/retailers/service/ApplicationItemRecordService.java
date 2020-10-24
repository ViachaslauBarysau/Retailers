package by.itechart.retailers.service;

import by.itechart.retailers.dto.ApplicationItemRecordDto;
import by.itechart.retailers.dto.CustomerDto;

import java.util.List;

public interface ApplicationItemRecordService {

    ApplicationItemRecordDto findById(long applicationItemRecordId);

    List<ApplicationItemRecordDto> findAll();

    ApplicationItemRecordDto create(ApplicationItemRecordDto applicationItemRecordDto);

    ApplicationItemRecordDto update(ApplicationItemRecordDto applicationItemRecordDto);

}
