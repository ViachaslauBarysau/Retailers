package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto create(ProductDto productDto) throws BusinessException;

    ProductDto update(ProductDto productDto);

    List<ProductDto> delete(List<ProductDto> productDtos);

    boolean upcExistsForCreate(Integer upc);

    boolean upcExistsForUpdate(Integer upc);
}
