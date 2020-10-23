package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.ApplicationItemRecordDto;
import by.itechart.retailers.dto.CustomerDto;
import by.itechart.retailers.service.ApplicationItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationItemRecordServiceImpl implements ApplicationItemRecordService {

    private ApplicationItemRecordRepository applicationItemRecordRepository;
    private AddressConverter addressConverter;

    @Autowired
    public ApplicationItemRecordServiceImpl(ApplicationItemRecordRepository applicationItemRecordRepository, AddressConverter addressConverter) {
        this.applicationItemRecordRepository = applicationItemRecordRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public ApplicationItemRecordDto findById(long applicationItemRecordId) {
        return null;
    }

    @Override
    public List<ApplicationItemRecordDto> findAll() {
        return null;
    }

    @Override
    public ApplicationItemRecordDto create(ApplicationItemRecordDto applicationItemRecordDto) {
        return null;
    }

    @Override
    public CustomerDto update(ApplicationItemRecordDto applicationItemRecordDto) {
        return null;
    }
}
