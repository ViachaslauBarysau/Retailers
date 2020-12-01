package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto findById(long categoryId);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);
}
