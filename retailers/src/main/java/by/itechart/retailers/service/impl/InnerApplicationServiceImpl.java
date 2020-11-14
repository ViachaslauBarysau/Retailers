package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.InnerApplicationConverter;
import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.entity.InnerApplication;
import by.itechart.retailers.repository.InnerApplicationRepository;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InnerApplicationServiceImpl implements InnerApplicationService {

    private final InnerApplicationRepository innerApplicationRepository;
    private final InnerApplicationConverter innerApplicationConverter;

    @Autowired
    public InnerApplicationServiceImpl(InnerApplicationRepository innerApplicationRepository,
                                       InnerApplicationConverter innerApplicationConverter) {
        this.innerApplicationRepository = innerApplicationRepository;
        this.innerApplicationConverter = innerApplicationConverter;
    }

    @Override
    public InnerApplicationDto findById(long innerApplicationId) {
        InnerApplication innerApplication = innerApplicationRepository.findById(innerApplicationId)
                .orElse(new InnerApplication());

        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public List<InnerApplicationDto> findAll(Pageable pageable) {
        Page<InnerApplication> innerApplicationPage = innerApplicationRepository.findAll(pageable);

        return innerApplicationConverter.entityToDto(innerApplicationPage.toList());
    }

    @Override
    public InnerApplicationDto create(InnerApplicationDto innerApplicationDto) {
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        InnerApplication persistInnerApplication = innerApplicationRepository.save(innerApplication);

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto update(InnerApplicationDto innerApplicationDto) {
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        InnerApplication persistInnerApplication = innerApplicationRepository.findById(innerApplication.getId())
                .orElse(new InnerApplication());

        persistInnerApplication.setApplicationNumber(innerApplication.getApplicationNumber());
        persistInnerApplication.setApplicationStatus(innerApplication.getApplicationStatus());
        persistInnerApplication.setCreator(innerApplication.getCreator());
        persistInnerApplication.setDestinationLocation(innerApplication.getDestinationLocation());
        persistInnerApplication.setRecordsList(innerApplication.getRecordsList());
        persistInnerApplication.setRegistrationDateTime(innerApplication.getRegistrationDateTime());
        persistInnerApplication.setSourceLocation(innerApplication.getSourceLocation());
        persistInnerApplication.setTotalProductAmount(innerApplication.getTotalProductAmount());
        persistInnerApplication.setTotalUnitNumber(innerApplication.getTotalUnitNumber());
        persistInnerApplication.setUpdater(innerApplication.getUpdater());
        persistInnerApplication.setUpdatingDateTime(innerApplication.getUpdatingDateTime());

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }
}
