package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto findById(long locationId);

    List<LocationDto> findAll(Pageable pageable);

    List<LocationDto> findAllWarehouses();

    List<LocationDto> findAllShops();

    LocationDto create(LocationDto locationDto) throws NotUniqueDataException;

    LocationDto update(LocationDto locationDto);

    List<LocationDto> delete(List<LocationDto> locationDtos);

    boolean identifierExists(String identifier);

}
