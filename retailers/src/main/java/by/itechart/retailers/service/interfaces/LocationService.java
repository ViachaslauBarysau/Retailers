package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto findById(long locationId);

    List<LocationDto> findAll();

    LocationDto create(LocationDto locationDto);

    LocationDto update(LocationDto locationDto);

    void delete(List<LocationDto> locationDtos);

}
