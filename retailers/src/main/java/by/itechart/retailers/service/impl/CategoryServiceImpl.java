package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CategoryConverter;
import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.entity.Category;
import by.itechart.retailers.repository.CategoryRepository;
import by.itechart.retailers.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public CategoryDto findById(long categoryId) {
        Category persistCategory = categoryRepository.findById(categoryId).orElse(new Category());
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryConverter.entityToDto(categoryList);
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryConverter.dtoToEntity(categoryDto);
        Category persistCategory = categoryRepository.save(category);
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = categoryConverter.dtoToEntity(categoryDto);
        Category persistCategory = categoryRepository.findById(category.getId()).orElse(new Category());

        persistCategory.setName(category.getName());
        persistCategory.setCategoryTax(category.getCategoryTax());

        return categoryConverter.entityToDto(persistCategory);
    }
}
