package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationProductDto;

import java.util.List;

public interface LocationProductService {
    LocationProductDto findById(long locationProductId);

    List<LocationProductDto> findAll();

    LocationProductDto create(LocationProductDto locationProductDto);

    LocationProductDto update(LocationProductDto locationProductDto);
}
