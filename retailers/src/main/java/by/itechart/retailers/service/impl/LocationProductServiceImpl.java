package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationProductConverter;
import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.service.interfaces.LocationProductService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProductServiceImpl implements LocationProductService {

    private final LocationProductRepository locationProductRepository;
    private final LocationProductConverter locationProductConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(LocationProductServiceImpl.class);

    @Autowired
    public LocationProductServiceImpl(LocationProductRepository locationProductRepository, LocationProductConverter locationProductConverter, UserService userService) {
        this.locationProductRepository = locationProductRepository;
        this.locationProductConverter = locationProductConverter;
        this.userService = userService;
    }


    @Override
    public LocationProductDto findById(long locationProductId) {
        logger.info("Find by id {}", locationProductId);
        LocationProduct locationProduct = locationProductRepository.findById(locationProductId)
                                                                   .orElse(new LocationProduct());

        return locationProductConverter.entityToDto(locationProduct);
    }

    @Override
    public Page<LocationProductDto> findAll(Pageable pageable) {
        logger.info("Find all");
        UserDto userDto = userService.getUser();
        Page<LocationProduct> locationProductPage = locationProductRepository.findAllByLocation_IdAndAmountGreaterThan(pageable, userDto.getLocation()
                                                                                                                                        .getId(), 0);
        List<LocationProductDto> locationProductDtos = locationProductConverter.entityToDto(locationProductPage.getContent());
        return new PageImpl<>(locationProductDtos, pageable, locationProductPage.getTotalElements());
    }

    @Override
    public LocationProductDto create(LocationProductDto locationProductDto) {
        logger.info("Create");
        LocationProduct locationProduct = locationProductConverter.dtoToEntity(locationProductDto);
        LocationProduct persistLocationProduct = locationProductRepository.save(locationProduct);

        return locationProductConverter.entityToDto(persistLocationProduct);
    }

    @Override
    public LocationProductDto update(LocationProductDto locationProductDto) {
        logger.info("Update");
        LocationProduct locationProduct = locationProductConverter.dtoToEntity(locationProductDto);
        LocationProduct persistLocationProduct = locationProductRepository.findById(locationProduct.getId())
                                                                          .orElse(new LocationProduct());

        persistLocationProduct.setCost(locationProduct.getCost());
        persistLocationProduct.setProduct(locationProduct.getProduct());
        persistLocationProduct = locationProductRepository.save(persistLocationProduct);

        return locationProductConverter.entityToDto(persistLocationProduct);
    }
}
