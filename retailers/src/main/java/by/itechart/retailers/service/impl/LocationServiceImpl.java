package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import by.itechart.retailers.entity.Status;
import by.itechart.retailers.repository.LocationRepository;
import by.itechart.retailers.repository.UserRepository;
import by.itechart.retailers.service.interfaces.LocationService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public List<LocationDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        Page<Location> locationPage = locationRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                               .getId());
        return locationConverter.entityToDto(locationPage.toList());
    }

    @Override
    public List<LocationDto> findAllWarehouses() {
        UserDto userDto = userService.getUser();
        List<Location> warehouses = locationRepository.findAllByCustomer_IdAndLocationType(userDto.getCustomer()
                                                                                                    .getId(), LocationType.WAREHOUSE);
        return locationConverter.entityToDto(warehouses);
    }

    @Override
    public List<LocationDto> findAllShops() {
        UserDto userDto = userService.getUser();
        List<Location> shopList = locationRepository.findAllByCustomer_IdAndLocationType(userDto.getCustomer()
                                                                                                    .getId(), LocationType.SHOP);
        return locationConverter.entityToDto(shopList);
    }

    @Override
    public LocationDto create(LocationDto locationDto) {
        Location location = locationConverter.dtoToEntity(locationDto);
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
        persistLocation.setWriteOffActList(location.getWriteOffActList());
        persistLocation.setLocationType(location.getLocationType());
        persistLocation.setTotalCapacity(location.getTotalCapacity());
        persistLocation = locationRepository.save(persistLocation);

        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public List<LocationDto> delete(List<LocationDto> locationDtos) {
        List<Location> locations = locationConverter.dtoToEntity(locationDtos);
        for (Location location : locations) {
            if (userRepository.findAllByLocation_IdAndUserStatus(location.getId(), Status.ACTIVE) == null) {
                location.setStatus(DeletedStatus.DELETED);
                locationRepository.save(location);
                locations.remove(location);
            }
        }
        return locationConverter.entityToDto(locations);
    }
}
