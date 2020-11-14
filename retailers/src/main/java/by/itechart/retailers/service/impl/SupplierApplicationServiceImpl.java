package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierApplicationConverter;
import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.entity.SupplierApplication;
import by.itechart.retailers.repository.SupplierApplicationRepository;
import by.itechart.retailers.service.interfaces.SupplierApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierApplicationServiceImpl implements SupplierApplicationService {

    private SupplierApplicationRepository supplierApplicationRepository;
    private SupplierApplicationConverter supplierApplicationConverter;

    @Autowired
    public SupplierApplicationServiceImpl(SupplierApplicationRepository supplierApplicationRepository,
                                          SupplierApplicationConverter supplierApplicationConverter) {
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.supplierApplicationConverter = supplierApplicationConverter;
    }

    @Override
    public SupplierApplicationDto findById(long supplierApplicationId) {
        SupplierApplication supplierApplication = supplierApplicationRepository.findById(supplierApplicationId)
                .orElse(new SupplierApplication());

        return supplierApplicationConverter.entityToDto(supplierApplication);
    }

    @Override
    public List<SupplierApplicationDto> findAll(Pageable pageable) {
        Page<SupplierApplication> supplierApplicationPage = supplierApplicationRepository.findAll(pageable);

        return supplierApplicationConverter.entityToDto(supplierApplicationPage.toList());
    }

    @Override
    public SupplierApplicationDto create(SupplierApplicationDto supplierApplicationDto) {
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        SupplierApplication persistSupplierApplication = supplierApplicationRepository.save(supplierApplication);

        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }

    @Override
    public SupplierApplicationDto update(SupplierApplicationDto supplierApplicationDto) {
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        SupplierApplication persistSupplierApplication = supplierApplicationRepository
                .findById(supplierApplication.getId()).orElse(new SupplierApplication());

        persistSupplierApplication.setApplicationNumber(supplierApplication.getApplicationNumber());
        persistSupplierApplication.setApplicationStatus(supplierApplication.getApplicationStatus());
        persistSupplierApplication.setCreator(supplierApplication.getCreator());
        persistSupplierApplication.setDestinationLocation(supplierApplication.getDestinationLocation());
        persistSupplierApplication.setRecordsList(supplierApplication.getRecordsList());
        persistSupplierApplication.setRegistrationDateTime(supplierApplication.getRegistrationDateTime());
        persistSupplierApplication.setSupplier(supplierApplication.getSupplier());
        persistSupplierApplication.setTotalProductAmount(supplierApplication.getTotalProductAmount());
        persistSupplierApplication.setTotalUnitNumber(supplierApplication.getTotalUnitNumber());
        persistSupplierApplication.setUpdater(supplierApplication.getUpdater());
        persistSupplierApplication.setUpdatingDateTime(supplierApplication.getUpdatingDateTime());

        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }
}
