package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ApplicationItemRecordConverter;
import by.itechart.retailers.dto.ApplicationItemRecordDto;
import by.itechart.retailers.entity.ApplicationItemRecord;
import by.itechart.retailers.repository.ApplicationItemRecordRepository;
import by.itechart.retailers.service.ApplicationItemRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationItemRecordServiceImpl implements ApplicationItemRecordService {

    private ApplicationItemRecordRepository applicationItemRecordRepository;
    private ApplicationItemRecordConverter applicationItemRecordConverter;

    @Autowired
    public ApplicationItemRecordServiceImpl(ApplicationItemRecordRepository applicationItemRecordRepository,
                                            ApplicationItemRecordConverter applicationItemRecordConverter) {
        this.applicationItemRecordRepository = applicationItemRecordRepository;
        this.applicationItemRecordConverter = applicationItemRecordConverter;
    }

    @Override
    public ApplicationItemRecordDto findById(long applicationItemRecordId) {
        ApplicationItemRecord itemRecord = applicationItemRecordRepository
                .findById(applicationItemRecordId)
                .orElse(new ApplicationItemRecord());

        return applicationItemRecordConverter.entityToDto(itemRecord);
    }

    @Override
    public List<ApplicationItemRecordDto> findAll() {
        List<ApplicationItemRecord> applicationItemRecordList = applicationItemRecordRepository.findAll();
        return applicationItemRecordConverter.entityToDto(applicationItemRecordList);
    }

    @Override
    public ApplicationItemRecordDto create(ApplicationItemRecordDto applicationItemRecordDto) {
        ApplicationItemRecord applicationItemRecord = applicationItemRecordConverter
                .dtoToEntity(applicationItemRecordDto);

        ApplicationItemRecord persistApplication = applicationItemRecordRepository.save(applicationItemRecord);
        return applicationItemRecordConverter.entityToDto(persistApplication);
    }

    @Override
    public ApplicationItemRecordDto update(ApplicationItemRecordDto applicationItemRecordDto) {
        ApplicationItemRecord applicationItemRecord = applicationItemRecordConverter
                .dtoToEntity(applicationItemRecordDto);

        ApplicationItemRecord persistApplicationItem = applicationItemRecordRepository
                .findApplicationItemRecordByItem(applicationItemRecord.getItem());

        persistApplicationItem.setAmount(applicationItemRecord.getAmount());
        persistApplicationItem.setCost(applicationItemRecord.getCost());

        return applicationItemRecordConverter.entityToDto(persistApplicationItem);
    }
}
