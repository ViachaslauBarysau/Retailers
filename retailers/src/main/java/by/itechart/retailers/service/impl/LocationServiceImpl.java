package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.interfaces.LocationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final UserService userService;
    private final LocationRepository locationRepository;
    private final LocationConverter locationConverter;
    private final UserRepository userRepository;

    @Autowired
    public LocationServiceImpl(UserService userService, LocationRepository locationRepository, LocationConverter locationConverter, UserRepository userRepository) {
        this.userService = userService;
        this.locationRepository = locationRepository;
        this.locationConverter = locationConverter;
        this.userRepository = userRepository;
    }


    @Override
    public LocationDto findById(long locationProductId) {
        Location location = locationRepository.findById(locationProductId)
                                              .orElse(new Location());

        return locationConverter.entityToDto(location);
    }

    @Override
    public Page<LocationDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        Page<Location> locationPage = locationRepository.findAllByCustomer_IdAndStatus(pageable, userDto.getCustomer()
                                                                                                        .getId(), DeletedStatus.ACTIVE);
        List<LocationDto> locationDtos = locationConverter.entityToDto(locationPage.getContent());
        return new PageImpl<>(locationDtos, pageable, locationPage.getTotalElements());
    }

    @Override
    public List<LocationDto> findAllWarehouses() {
        UserDto userDto = userService.getUser();
        List<Location> warehouses = locationRepository.findAllByCustomer_IdAndLocationTypeAndStatus(userDto.getCustomer()
                                                                                                           .getId(), LocationType.WAREHOUSE, DeletedStatus.ACTIVE);
        return locationConverter.entityToDto(warehouses);
    }

    @Override
    public List<LocationDto> findAllShops() {
        UserDto userDto = userService.getUser();
        List<Location> shopList = locationRepository.findAllByCustomer_IdAndLocationTypeAndStatus(userDto.getCustomer()
                                                                                                         .getId(), LocationType.SHOP, DeletedStatus.ACTIVE);
        return locationConverter.entityToDto(shopList);
    }

    @Override
    public LocationDto create(LocationDto locationDto) throws BusinessException {
        Location location = locationConverter.dtoToEntity(locationDto);
        if (identifierExists(location.getIdentifier())) {
            throw new BusinessException("Identifier should be unique");
        }
        Location persistLocation = locationRepository.save(location);

        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public LocationDto update(LocationDto locationDto) {
        Location location = locationConverter.dtoToEntity(locationDto);
        Location persistLocation = locationRepository.findById(location.getId())
                                                     .orElse(new Location());

        persistLocation.setAddress(location.getAddress());
        persistLocation.setAvailableCapacity(location.getAvailableCapacity());
        persistLocation.setCustomer(location.getCustomer());
        persistLocation.setIdentifier(location.getIdentifier());
        persistLocation.setLocationType(location.getLocationType());
        persistLocation.setTotalCapacity(location.getTotalCapacity());
        persistLocation = locationRepository.save(persistLocation);

        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public List<LocationDto> delete(List<LocationDto> locationDtos) {
        List<Location> locations = locationConverter.dtoToEntity(locationDtos);
        for (Location location : locations) {
            if (userRepository.findAllByLocation_IdAndUserStatus(location.getId(), Status.ACTIVE)
                              .size() == 0) {
                location.setStatus(DeletedStatus.DELETED);
                locationRepository.save(location);
                locations.remove(location);
            }
        }
        return locationConverter.entityToDto(locations);
    }

    @Override
    public boolean identifierExists(String identifier) {
        return locationRepository.findByIdentifier(identifier)
                                 .isPresent();
    }
}
