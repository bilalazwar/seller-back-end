package com.TechTron.sellerbackend.controller;

import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Category Controller",description = "Manages Categories")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Create new Category", description = "Creates and returns the new Category")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        Category category1 = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category1);
    }
    @Operation(summary = "List of all Categories", description = "Returns all the categories ")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategory());
    }

    @Operation(summary = "Get Category by Id", description = "Returns the Category related to the ID ")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Delete Category by Id", description = "Deletes the Category related to the Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @Operation(summary = "Update Category by Id", description = "Updates & returns the Category related to the Id")
    @PatchMapping("/{id}")
    public Category updateCategoryById(@PathVariable int id, @RequestBody Category category){

        return categoryService.updateCategory(id,category);
    }

}
