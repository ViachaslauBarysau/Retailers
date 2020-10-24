package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationItemConverter;
import by.itechart.retailers.dto.LocationItemDto;
import by.itechart.retailers.entity.LocationItem;
import by.itechart.retailers.repository.LocationItemRepository;
import by.itechart.retailers.service.LocationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationItemServiceImpl implements LocationItemService {

    private LocationItemRepository locationItemRepository;
    private LocationItemConverter locationItemConverter;

    @Autowired
    public LocationItemServiceImpl(LocationItemRepository locationItemRepository,
                                   LocationItemConverter locationItemConverter) {
        this.locationItemRepository = locationItemRepository;
        this.locationItemConverter = locationItemConverter;
    }

    @Override
    public LocationItemDto findById(long locationItemId) {
        LocationItem locationItem = locationItemRepository.findById(locationItemId)
                .orElse(new LocationItem());

        return locationItemConverter.entityToDto(locationItem);
    }

    @Override
    public List<LocationItemDto> findAll() {
        List<LocationItem> locationItemList = locationItemRepository.findAll();

        return locationItemConverter.entityToDto(locationItemList);
    }

    @Override
    public LocationItemDto create(LocationItemDto locationItemDto) {
        LocationItem locationItem = locationItemConverter.dtoToEntity(locationItemDto);
        LocationItem persistLocationItem = locationItemRepository.save(locationItem);

        return locationItemConverter.entityToDto(persistLocationItem);
    }

    @Override
    public LocationItemDto update(LocationItemDto locationItemDto) {
        LocationItem locationItem = locationItemConverter.dtoToEntity(locationItemDto);
        LocationItem persistLocationItem = locationItemRepository
                .findById(locationItem.getId())
                .orElse(new LocationItem());

        persistLocationItem.setCost(locationItem.getCost());
        persistLocationItem.setItem(locationItem.getItem());

        return locationItemConverter.entityToDto(persistLocationItem);
    }
}
