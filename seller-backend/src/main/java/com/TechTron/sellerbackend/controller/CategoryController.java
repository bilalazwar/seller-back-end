package com.TechTron.sellerbackend.controller;

import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        Category category1 = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category1);
    }
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @PatchMapping("/{id}")
    public Category updateCategoryById(@PathVariable int id, @RequestBody Category category){

        return categoryService.updateCategory(id,category);
    }

}
