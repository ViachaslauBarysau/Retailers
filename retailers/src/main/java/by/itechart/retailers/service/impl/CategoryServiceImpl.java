package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.AddressConverter;
import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private AddressConverter addressConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, AddressConverter addressConverter) {
        this.categoryRepository = categoryRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public CategoryDto findById(long categoryId) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return null;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        return null;
    }
}
