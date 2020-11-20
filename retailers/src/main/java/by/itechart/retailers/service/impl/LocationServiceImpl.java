package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CustomerConverter;
import by.itechart.retailers.converter.LocationConverter;
import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.repository.LocationRepository;
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

    @Autowired

    public LocationServiceImpl(LocationRepository locationRepository, LocationConverter locationConverter, CustomerConverter customerConverter, UserService userService) {
        this.locationRepository = locationRepository;
        this.locationConverter = locationConverter;
        this.userService = userService;
    }


    @Override
    public LocationDto findById(long locationProductId) {
        Location location = locationRepository.findById(locationProductId)
                                              .orElse(new Location());

        return locationConverter.entityToDto(location);
    }

    @Override
    public List<LocationDto> findAll(Pageable pageable) {
        //List<Location> locationList = locationRepository.findAll();
        UserDto userDto = userService.getUser();
        Page<Location> locationPage = locationRepository.findAllByCustomer_Id(pageable,userDto.getCustomer()
                                                                                              .getId());
        return locationConverter.entityToDto(locationPage.toList());
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
        persistLocation.setProductList(location.getProductList());
        persistLocation.setWriteOffActList(location.getWriteOffActList());
        persistLocation.setLocationType(location.getLocationType());
        persistLocation.setTotalCapacity(location.getTotalCapacity());
        persistLocation=locationRepository.save(persistLocation);

        return locationConverter.entityToDto(persistLocation);
    }

    @Override
    public void delete(List<LocationDto> locationDtos) {
        //подождать насчет проверки если ли юзеры в location
        List<Location> locations = locationConverter.dtoToEntity(locationDtos);
        for (Location location : locations) {
            locationRepository.delete(location);
        }
    }
}
