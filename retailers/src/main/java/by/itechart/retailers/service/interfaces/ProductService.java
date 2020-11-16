package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    List<ProductDto> findAll(Pageable pageable);

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto);
}
