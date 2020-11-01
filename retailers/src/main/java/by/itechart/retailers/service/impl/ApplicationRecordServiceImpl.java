package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ApplicationRecordConverter;
import by.itechart.retailers.dto.ApplicationRecordDto;
import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.repository.ApplicationRecordRepository;
import by.itechart.retailers.service.ApplicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRecordServiceImpl implements ApplicationRecordService {

    private ApplicationRecordRepository applicationRecordRepository;
    private ApplicationRecordConverter applicationRecordConverter;

    @Autowired
    public ApplicationRecordServiceImpl(ApplicationRecordRepository applicationRecordRepository,
                                        ApplicationRecordConverter applicationRecordConverter) {
        this.applicationRecordRepository = applicationRecordRepository;
        this.applicationRecordConverter = applicationRecordConverter;
    }

    @Override
    public ApplicationRecordDto findById(long applicationProductRecordId) {
        ApplicationRecord productRecord = applicationRecordRepository
                .findById(applicationProductRecordId)
                .orElse(new ApplicationRecord());

        return applicationRecordConverter.entityToDto(productRecord);
    }

    @Override
    public List<ApplicationRecordDto> findAll() {
        List<ApplicationRecord> applicationRecordList = applicationRecordRepository.findAll();
        return applicationRecordConverter.entityToDto(applicationRecordList);
    }

    @Override
    public ApplicationRecordDto create(ApplicationRecordDto applicationRecordDto) {
        ApplicationRecord applicationRecord = applicationRecordConverter
                .dtoToEntity(applicationRecordDto);

        ApplicationRecord persistApplication = applicationRecordRepository.save(applicationRecord);
        return applicationRecordConverter.entityToDto(persistApplication);
    }

    @Override
    public ApplicationRecordDto update(ApplicationRecordDto applicationRecordDto) {
        ApplicationRecord applicationRecord = applicationRecordConverter
                .dtoToEntity(applicationRecordDto);

        ApplicationRecord persistApplicationProduct = applicationRecordRepository
                .findApplicationProductRecordByProduct(applicationRecord.getProduct());

        persistApplicationProduct.setAmount(applicationRecord.getAmount());
        persistApplicationProduct.setCost(applicationRecord.getCost());

        return applicationRecordConverter.entityToDto(persistApplicationProduct);
    }
}
