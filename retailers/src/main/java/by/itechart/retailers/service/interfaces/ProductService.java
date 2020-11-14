package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    List<ProductDto> findAll();

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto);
}
