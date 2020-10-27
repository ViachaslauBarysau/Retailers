package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.LocationProductConverter;
import by.itechart.retailers.dto.LocationProductDto;
import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.repository.LocationProductRepository;
import by.itechart.retailers.service.LocationProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationProductServiceImpl implements LocationProductService {

    private LocationProductRepository locationProductRepository;
    private LocationProductConverter locationProductConverter;

    @Autowired
    public LocationProductServiceImpl(LocationProductRepository locationProductRepository,
                                      LocationProductConverter locationProductConverter) {
        this.locationProductRepository = locationProductRepository;
        this.locationProductConverter = locationProductConverter;
    }

    @Override
    public LocationProductDto findById(long locationProductId) {
        LocationProduct locationProduct = locationProductRepository.findById(locationProductId)
                .orElse(new LocationProduct());

        return locationProductConverter.entityToDto(locationProduct);
    }

    @Override
    public List<LocationProductDto> findAll() {
        List<LocationProduct> locationProductList = locationProductRepository.findAll();

        return locationProductConverter.entityToDto(locationProductList);
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

        return locationProductConverter.entityToDto(persistLocationProduct);
    }
}