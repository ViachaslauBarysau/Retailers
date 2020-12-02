package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.WriteOffActConverter;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.entity.WriteOffAct;
import by.itechart.retailers.entity.WriteOffActRecord;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.WriteOffActRepository;
import by.itechart.retailers.service.interfaces.UserService;
import by.itechart.retailers.service.interfaces.WriteOffActService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WriteOffActServiceImpl implements WriteOffActService {

    private final WriteOffActRepository writeOffActRepository;
    private final WriteOffActConverter writeOffActConverter;
    private final LocationProductRepository locationProductRepository;
    private final UserService userService;
    private final LocationRepository locationRepository;
    Logger logger = LoggerFactory.getLogger(WriteOffActServiceImpl.class);

    @Autowired
    public WriteOffActServiceImpl(WriteOffActRepository writeOffActRepository, WriteOffActConverter converter, LocationProductRepository locationProductRepository, UserService userService, LocationRepository locationRepository) {
        this.writeOffActRepository = writeOffActRepository;
        this.writeOffActConverter = converter;
        this.locationProductRepository = locationProductRepository;
        this.userService = userService;
        this.locationRepository = locationRepository;
    }


    @Override
    public WriteOffActDto findById(long writeOffActId) {
        logger.info("Find by id {}",writeOffActId);
        WriteOffAct writeOffAct = writeOffActRepository.findById(writeOffActId)
                                                       .orElse(new WriteOffAct());

        return writeOffActConverter.entityToDto(writeOffAct);
    }

    @Override
    public Page<WriteOffActDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        List<Location> locations = locationRepository.findAllByCustomer_Id(userDto.getCustomer()
                                                                                  .getId());
        Page<WriteOffAct> writeOffActPage = writeOffActRepository.findAllByLocationIn(pageable, locations);
        List<WriteOffActDto> writeOffActDtos = writeOffActConverter.entityToDto(writeOffActPage.getContent());
        return new PageImpl<>(writeOffActDtos, pageable, writeOffActPage.getTotalElements());

    }

    @Override
    @Transactional
    public WriteOffActDto create(WriteOffActDto writeOffActDto) throws BusinessException {
        logger.info("Create");
        WriteOffAct writeOffAct = writeOffActConverter.dtoToEntity(writeOffActDto);
        if (writeOffActNumberExists(writeOffAct.getWriteOffActNumber())) {
            logger.error("Not unique number {}", writeOffAct.getWriteOffActNumber());
            throw new BusinessException("Write-off act number should be unique");
        }
        Location location = writeOffAct.getLocation();
        List<WriteOffActRecord> writeOffActRecords = writeOffAct.getWriteOffActRecords();
        for (WriteOffActRecord writeOffActRecord : writeOffActRecords) {
            Long productId = writeOffActRecord.getProduct()
                                              .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);

            if (writeOffActRecord.getAmount() > locationProduct.getAmount()) {
                logger.error("Not enough product {} in location", locationProduct.getProduct()
                                                                                 .getId());

                throw new BusinessException("Not enough amount of " + locationProduct.getProduct()
                                                                                     .getLabel() + " in location " + locationProduct.getLocation()
                                                                                                                                     .getIdentifier());
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity - writeOffActRecord.getAmount() * writeOffActRecord.getProduct()
                                                                                                               .getVolume());
            writeOffAct.setLocation(location);
        }
        WriteOffAct persistWriteOffAct = writeOffActRepository.save(writeOffAct);

        return writeOffActConverter.entityToDto(persistWriteOffAct);
    }

    @Override
    public boolean writeOffActNumberExists(Integer writeOffActNumber) {
        logger.info("Check for existing number {}", writeOffActNumber);
        return writeOffActRepository.findByWriteOffActNumber(writeOffActNumber)
                                    .isPresent();
    }

}
