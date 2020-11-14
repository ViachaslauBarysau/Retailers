package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto findById(long categoryId);

    List<CategoryDto> findAll();

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);
}
