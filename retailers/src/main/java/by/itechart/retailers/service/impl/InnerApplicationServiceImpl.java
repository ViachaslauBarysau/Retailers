package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.InnerApplicationConverter;
import by.itechart.retailers.converter.UserConverter;
import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.InnerApplicationRepository;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.service.interfaces.InnerApplicationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    Logger logger = LoggerFactory.getLogger(InnerApplicationServiceImpl.class);

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
        logger.info("Find by id {}", innerApplicationId);
        InnerApplication innerApplication = innerApplicationRepository.findById(innerApplicationId)
                                                                      .orElse(new InnerApplication());

        return innerApplicationConverter.entityToDto(innerApplication);
    }

    @Override
    public Page<InnerApplicationDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        Page<InnerApplication> innerApplicationPage = innerApplicationRepository.findAllByDestinationLocation_Id(pageable, userDto.getLocation()
                                                                                                                                  .getId());
        List<InnerApplicationDto> innerApplicationDtos = innerApplicationConverter.entityToDto(innerApplicationPage.getContent());
        return new PageImpl<>(innerApplicationDtos, pageable, innerApplicationPage.getTotalElements());

    }

    @Override
    @Transactional
    public InnerApplicationDto create(InnerApplicationDto innerApplicationDto) throws BusinessException {
        logger.info("Create");
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        Location location = innerApplication.getSourceLocation();
        if (applicationNumberExistsForCreate(innerApplication.getApplicationNumber())) {
            logger.error("Not unique number {}", innerApplication.getApplicationNumber());
            throw new BusinessException("Application number should be unique");
        }
        List<ApplicationRecord> applicationRecords = innerApplication.getRecordsList();
        for (ApplicationRecord applicationRecord : applicationRecords) {
            Long productId = applicationRecord.getProduct()
                                              .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);

            if (applicationRecord.getAmount() > locationProduct.getAmount()) {
                logger.error("Not enough product {} in location", locationProduct.getProduct()
                                                                                 .getId());
                throw new BusinessException("Not enough amount of " + locationProduct.getAmount() + " in location " + locationProduct.getLocation()
                                                                                                                                     .getIdentifier());
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity - applicationRecord.getAmount() * applicationRecord.getProduct()
                                                                                                               .getVolume());
            innerApplication.setSourceLocation(location);
        }
        InnerApplication persistInnerApplication = innerApplicationRepository.save(innerApplication);

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto update(InnerApplicationDto innerApplicationDto) {
        logger.info("Update");
        InnerApplication innerApplication = innerApplicationConverter.dtoToEntity(innerApplicationDto);
        InnerApplication persistInnerApplication = innerApplicationRepository.findById(innerApplication.getId())
                                                                             .orElse(new InnerApplication());

        persistInnerApplication.setDestinationLocation(innerApplication.getDestinationLocation());
        persistInnerApplication = innerApplicationRepository.save(persistInnerApplication);

        return innerApplicationConverter.entityToDto(persistInnerApplication);
    }

    @Override
    public InnerApplicationDto updateStatus(Long innerApplicationId) throws BusinessException {
        logger.info("Update status {}", innerApplicationId);
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
    public boolean applicationNumberExistsForCreate(Integer applicationNumber) {
        UserDto userDto = userService.getUser();
        User user = userConverter.dtoToEntity(userDto);
        return innerApplicationRepository.findAllByApplicationNumberAndCreator(applicationNumber, user)
                                         .size() != 0;
    }

}
