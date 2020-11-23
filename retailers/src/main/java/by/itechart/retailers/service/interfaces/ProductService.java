package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    List<ProductDto> findAll(Pageable pageable);

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    List<ProductDto> delete(List<Long> productIds);
}
