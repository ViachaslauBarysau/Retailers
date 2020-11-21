package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto findById(long locationId);

    List<LocationDto> findAll(Pageable pageable);

    LocationDto create(LocationDto locationDto);

    LocationDto update(LocationDto locationDto);

    List<LocationDto>  delete(List<LocationDto> locationDtos);

}
