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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WriteOffActServiceImpl implements WriteOffActService {

    private final WriteOffActRepository writeOffActRepository;
    private final WriteOffActConverter converter;
    private final LocationProductRepository locationProductRepository;
    private final UserService userService;
    private final LocationRepository locationRepository;

    @Autowired
    public WriteOffActServiceImpl(WriteOffActRepository writeOffActRepository, WriteOffActConverter converter, LocationProductRepository locationProductRepository, UserService userService, LocationRepository locationRepository) {
        this.writeOffActRepository = writeOffActRepository;
        this.converter = converter;
        this.locationProductRepository = locationProductRepository;
        this.userService = userService;
        this.locationRepository = locationRepository;
    }


    @Override
    public WriteOffActDto findById(long writeOffActId) {
        WriteOffAct writeOffAct = writeOffActRepository.findById(writeOffActId)
                .orElse(new WriteOffAct());

        return converter.entityToDto(writeOffAct);
    }

    @Override
    public List<WriteOffActDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        List<Location> locations = locationRepository.findAllByCustomer_Id(userDto.getCustomer()
                .getId());
        Page<WriteOffAct> writeOffActPage = writeOffActRepository.findAllByLocationIn(pageable, locations);

        return converter.entityToDto(writeOffActPage.toList());
    }

    @Override
    @Transactional
    public WriteOffActDto create(WriteOffActDto writeOffActDto) throws BusinessException {
        WriteOffAct writeOffAct = converter.dtoToEntity(writeOffActDto);
        if (writeOffActNumberExists(writeOffAct.getWriteOffActNumber())) {
            throw new BusinessException("Write-off act number should be unique");
        }
        Location location = writeOffAct.getLocation();
        List<WriteOffActRecord> writeOffActRecords = writeOffAct.getWriteOffActRecords();
        for (WriteOffActRecord writeOffActRecord : writeOffActRecords) {
            Long productId = writeOffActRecord.getProduct()
                    .getId();
            LocationProduct locationProduct = locationProductRepository.findByLocation_IdAndProduct_Id(location.getId(), productId);

            if (writeOffActRecord.getAmount() > locationProduct.getAmount()) {
                throw new BusinessException("Not enough amount of " + locationProduct.getAmount() + " in location " + locationProduct.getLocation()
                        .getIdentifier());
            }
            Integer availableCapacity = location.getAvailableCapacity();
            location.setAvailableCapacity(availableCapacity - writeOffActRecord.getAmount() * writeOffActRecord.getProduct().getVolume());
            writeOffAct.setLocation(location);
        }
        WriteOffAct persistWriteOffAct = writeOffActRepository.save(writeOffAct);

        return converter.entityToDto(persistWriteOffAct);
    }

    @Override
    public boolean writeOffActNumberExists(Integer writeOffActNumber) {
        return writeOffActRepository.findByWriteOffActNumber(writeOffActNumber)
                .isPresent();
    }

}
