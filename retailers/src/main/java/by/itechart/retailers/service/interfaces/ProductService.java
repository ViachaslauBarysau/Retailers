package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.exceptions.NotUniqueDataException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    List<ProductDto> findAll(Pageable pageable);

    ProductDto create(ProductDto productDto) throws NotUniqueDataException;

    ProductDto update(ProductDto productDto);

    List<ProductDto> delete(List<ProductDto> productDtos);

    boolean upcExists(Integer upc, DeletedStatus status);
}
