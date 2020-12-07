package by.itechart.retailers.controller;

import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity findAll(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity findById(@PathVariable(name = "categoryId") Long categoryId) {
        return new ResponseEntity<>(categoryService.findById(categoryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.update(categoryDto), HttpStatus.OK);
    }
}
