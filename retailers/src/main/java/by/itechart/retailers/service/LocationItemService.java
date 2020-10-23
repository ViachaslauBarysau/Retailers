package by.itechart.retailers.service;

import by.itechart.retailers.dto.LocationItemDto;

import java.util.List;

public interface LocationItemService {
    LocationItemDto findById(long locationItemId);

    List<LocationItemDto> findAll();

    LocationItemDto create(LocationItemDto locationItemDto);

    LocationItemDto update(LocationItemDto locationItemDto);
}
