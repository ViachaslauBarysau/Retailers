package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.InnerApplicationConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import by.itechart.retailers.repository.InnerApplicationRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InnerApplicationServiceImpl implements InnerApplicationService {

    private final InnerApplicationRepository innerApplicationRepository;
    private final InnerApplicationConverter innerApplicationConverter;
    private final UserConverter userConverter;
    private final UserService userService;
    private final LocationProductRepository locationProductRepository;

    @Autowired
    public InnerApplicationServiceImpl(InnerApplicationRepository innerApplicationRepository, InnerApplicationConverter innerApplicationConverter, UserConverter userConverter, UserService userService, LocationProductRepository locationProductRepository) {
        this.innerApplicationRepository = innerApplicationRepository;
        this.innerApplicationConverter = innerApplicationConverter;
        this.userConverter = userConverter;
        this.userService = userService;
        this.locationProductRepository = locationProductRepository;
    }


    @Override
    public InnerApplicationDto findById(long innerApplicationId) {
        InnerApplication innerApplication = innerApplicationRepository.findById(innerApplicationId)
                                                                      .orElse(new InnerApplication());

        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public List<InnerApplicationDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        Page<InnerApplication> innerApplicationPage = innerApplicationRepository.findAllByDestinationLocation_Id(pageable, userDto.getLocation()
                                                                                                                                  .getId());

        return innerApplicationConverter.entityToDto(innerApplicationPage.toList());
    }

    @Override
    @Transactional
    public InnerApplicationDto create(InnerApplicationDto innerApplicationDto) throws NotUniqueDataException {
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        Location location = innerApplication.getSourceLocation();
        if (applicationNumberExists(innerApplication.getApplicationNumber())) {
            throw new NotUniqueDataException("Application number should be unique");
        }
        List<ApplicationRecord> applicationRecords = innerApplication.getRecordsList();
        for (ApplicationRecord applicationRecord : applicationRecords) {
            Long productId = applicationRecord.getProduct()
                                              .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);

            if (applicationRecord.getAmount() > locationProduct.getAmount()) {
                throw new BusinessException("Not enough amount of " + locationProduct.getAmount() + " in location " + locationProduct.getLocation()
                                                                                                                                     .getIdentifier());
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity - applicationRecord.getAmount());
            innerApplication.setSourceLocation(location);
        }
        InnerApplication persistInnerApplication = innerApplicationRepository.save(innerApplication);

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto update(InnerApplicationDto innerApplicationDto) {
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        InnerApplication persistInnerApplication = innerApplicationRepository.findById(innerApplication.getId())
                                                                             .orElse(new InnerApplication());

        persistInnerApplication.setDestinationLocation(innerApplication.getDestinationLocation());
        persistInnerApplication = innerApplicationRepository.save(persistInnerApplication);

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto updateStatus(Long innerApplicationId) throws BusinessException {
        UserDto userDto = userService.getUser();
        InnerApplication innerApplication = innerApplicationRepository.findById(innerApplicationId)
                                                                      .get();

        Location location = innerApplication.getDestinationLocation();
        Integer totalUnitAmount = innerApplication.getTotalUnitNumber();
        if (totalUnitAmount > location.getAvailableCapacity()) {
            throw new BusinessException("Not enough space in destination location");
        }

        List<ApplicationRecord> applicationRecords = innerApplication.getRecordsList();
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
        innerApplication.setDestinationLocation(location);
        innerApplication.setApplicationStatus(ApplicationStatus.FINISHED_PROCESSING);
        innerApplication.setUpdater(userConverter.dtoToEntity(userDto));
        innerApplication.setUpdatingDateTime(LocalDateTime.now());
        innerApplicationRepository.save(innerApplication);
        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public boolean applicationNumberExists(Integer applicationNumber) {
        return innerApplicationRepository.findByApplicationNumber(applicationNumber)
                                         .isPresent();
    }
}
