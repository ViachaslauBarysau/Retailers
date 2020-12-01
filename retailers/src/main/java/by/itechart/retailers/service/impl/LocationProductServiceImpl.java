package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationProductConverter;
import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.service.interfaces.LocationProductService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProductServiceImpl implements LocationProductService {

    private final LocationProductRepository locationProductRepository;
    private final LocationProductConverter locationProductConverter;
    private final UserService userService;

    @Autowired
    public LocationProductServiceImpl(LocationProductRepository locationProductRepository, LocationProductConverter locationProductConverter, UserService userService) {
        this.locationProductRepository = locationProductRepository;
        this.locationProductConverter = locationProductConverter;
        this.userService = userService;
    }


    @Override
    public LocationProductDto findById(long locationProductId) {
        LocationProduct locationProduct = locationProductRepository.findById(locationProductId)
                                                                   .orElse(new LocationProduct());

        return locationProductConverter.entityToDto(locationProduct);
    }

    @Override
    public List<LocationProductDto> findAll(Pageable pageable) {

        UserDto userDto = userService.getUser();
        Page<LocationProduct> locationProductPage = locationProductRepository.findAllByLocation_IdAndAmountGreaterThan(pageable, userDto.getLocation()
                                                                                                                                        .getId(), 0);

        return locationProductConverter.entityToDto(locationProductPage.toList());
    }

    @Override
    public LocationProductDto create(LocationProductDto locationProductDto) {
        LocationProduct locationProduct = locationProductConverter.dtoToEntity(locationProductDto);
        LocationProduct persistLocationProduct = locationProductRepository.save(locationProduct);

        return locationProductConverter.entityToDto(persistLocationProduct);
    }

    @Override
    public LocationProductDto update(LocationProductDto locationProductDto) {
        LocationProduct locationProduct = locationProductConverter.dtoToEntity(locationProductDto);
        LocationProduct persistLocationProduct = locationProductRepository
                .findById(locationProduct.getId())
                .orElse(new LocationProduct());

        persistLocationProduct.setCost(locationProduct.getCost());
        persistLocationProduct.setProduct(locationProduct.getProduct());
        persistLocationProduct = locationProductRepository.save(persistLocationProduct);

        return locationProductConverter.entityToDto(persistLocationProduct);
    }
}
