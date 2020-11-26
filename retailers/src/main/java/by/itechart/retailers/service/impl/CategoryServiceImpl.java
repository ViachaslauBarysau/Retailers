package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CategoryConverter;
import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Category;
import by.itechart.retailers.repository.CategoryRepository;
import by.itechart.retailers.service.interfaces.CategoryService;
import by.itechart.retailers.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final UserService userService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.userService = userService;
    }

    @Override
    public CategoryDto findById(long categoryId) {
        Category persistCategory = categoryRepository.findById(categoryId)
                                                     .orElse(new Category());
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        UserDto userDto = userService.getUser();
        Page<Category> categoryPage = categoryRepository.findAllByCustomer_Id(pageable, userDto.getCustomer()
                                                                                               .getId());
        return categoryConverter.entityToDto(categoryPage.toList());
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
        Category persistCategory = categoryRepository.findById(category.getId())
                                                     .orElse(new Category());

        persistCategory.setName(category.getName());
        persistCategory.setCategoryTax(category.getCategoryTax());
        persistCategory.setCustomer(category.getCustomer());
        persistCategory = categoryRepository.save(persistCategory);

        return categoryConverter.entityToDto(persistCategory);
    }
}
