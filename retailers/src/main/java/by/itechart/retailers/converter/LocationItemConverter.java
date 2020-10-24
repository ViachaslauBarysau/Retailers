package by.itechart.retailers.converter;

import by.itechart.retailers.dto.LocationItemDto;
import by.itechart.retailers.entity.LocationItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationItemConverter {
    public LocationItemDto entityToDto(LocationItem locationItem) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(locationItem, LocationItemDto.class);

    }

    public LocationItem dtoToEntity(LocationItemDto locationItemDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(locationItemDto, LocationItem.class);

    }

    public List<LocationItemDto> entityToDto(List<LocationItem> locations) {
        return locations.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<LocationItem> dtoToEntity(List<LocationItemDto> locationDtos) {
        return locationDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
