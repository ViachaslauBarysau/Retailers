package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierApplicationConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.SupplierApplicationRepository;
import by.itechart.retailers.service.interfaces.SupplierApplicationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SupplierApplicationServiceImpl implements SupplierApplicationService {

    private final SupplierApplicationRepository supplierApplicationRepository;
    private final LocationProductRepository locationProductRepository;
    private final SupplierApplicationConverter supplierApplicationConverter;
    private final UserConverter userConverter;
    private final UserService userService;

    public SupplierApplicationServiceImpl(SupplierApplicationRepository supplierApplicationRepository, LocationProductRepository locationProductRepository, SupplierApplicationConverter supplierApplicationConverter, UserConverter userConverter, UserService userService) {
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.locationProductRepository = locationProductRepository;
        this.supplierApplicationConverter = supplierApplicationConverter;
        this.userConverter = userConverter;
        this.userService = userService;
    }


    @Override
    public SupplierApplicationDto findById(long supplierApplicationId) {
        SupplierApplication supplierApplication = supplierApplicationRepository.findById(supplierApplicationId)
                                                                               .orElse(new SupplierApplication());

        return supplierApplicationConverter.entityToDto(supplierApplication);
    }

    @Override
    public List<SupplierApplicationDto> findAll(Pageable pageable) {
        UserDto userDto=userService.getUser();
        Page<SupplierApplication> supplierApplicationPage = supplierApplicationRepository.findAllByDestinationLocation_Id(pageable, userDto.getLocation().getId());


        return supplierApplicationConverter.entityToDto(supplierApplicationPage.toList());
    }

    @Override
    public SupplierApplicationDto create(SupplierApplicationDto supplierApplicationDto) throws BusinessException {
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        if (applicationNumberExists(supplierApplication.getApplicationNumber())) {
            throw new BusinessException("Application number should be unique");
        }
        SupplierApplication persistSupplierApplication = supplierApplicationRepository.save(supplierApplication);

        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }

    @Override
    public SupplierApplicationDto update(SupplierApplicationDto supplierApplicationDto) {
        SupplierApplication supplierApplication = supplierApplicationConverter.dtoToEntity(supplierApplicationDto);
        SupplierApplication persistSupplierApplication = supplierApplicationRepository
                .findById(supplierApplication.getId())
                .orElse(new SupplierApplication());

        persistSupplierApplication.setDestinationLocation(supplierApplication.getDestinationLocation());
        persistSupplierApplication = supplierApplicationRepository.save(persistSupplierApplication);

        return supplierApplicationConverter.entityToDto(persistSupplierApplication);
    }

    @Override
    public SupplierApplicationDto updateStatus(Long supplierApplicationId) throws BusinessException {
        UserDto userDto = userService.getUser();
        SupplierApplication supplierApplication = supplierApplicationRepository.findById(supplierApplicationId)
                                                                               .get();

        Location location = supplierApplication.getDestinationLocation();
        Integer totalUnitAmount = supplierApplication.getTotalUnitNumber();
        if (totalUnitAmount > location.getAvailableCapacity()) {
            throw new BusinessException("Not enough space in destination location");
        }

        List<ApplicationRecord> applicationRecords = supplierApplication.getRecordsList();
        for (ApplicationRecord applicationRecord : applicationRecords) {

            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), applicationRecord.getProduct()
                                                                                                                                          .getId());
            if (locationProduct != null) {
                if (locationProduct.getAmount() == 0) {
                    locationProduct.setCost(applicationRecord.getCost());
                    locationProduct.setAmount(applicationRecord.getAmount());
                    locationProductRepository.save(locationProduct);

                } else {
                    if (locationProduct.getCost()
                                       .compareTo(applicationRecord.getCost()) < 0) {
                        locationProduct.setCost(applicationRecord.getCost());
                        Integer amount = locationProduct.getAmount();
                        locationProduct.setAmount(amount + applicationRecord.getAmount());
                        locationProductRepository.save(locationProduct);
                    }
                }
            } else {
                locationProduct = new LocationProduct();
                locationProduct.setProduct(applicationRecord.getProduct());
                locationProduct.setCost(applicationRecord.getCost());
                locationProduct.setAmount(applicationRecord.getAmount());
                locationProduct.setLocation(location);
                locationProductRepository.save(locationProduct);
            }

        }
        Integer availableCapacity = location.getAvailableCapacity();
        location.setAvailableCapacity(availableCapacity - totalUnitAmount);
        supplierApplication.setDestinationLocation(location);
        supplierApplication.setApplicationStatus(ApplicationStatus.FINISHED_PROCESSING);
        supplierApplication.setUpdater(userConverter.dtoToEntity(userDto));
        supplierApplication.setUpdatingDateTime(LocalDateTime.now());
        supplierApplicationRepository.save(supplierApplication);
        return supplierApplicationConverter.entityToDto(supplierApplication);
    }

    @Override
    public boolean applicationNumberExists(Integer applicationNumber) {
        return supplierApplicationRepository.findByApplicationNumber(applicationNumber)
                                            .isPresent();
    }
}
