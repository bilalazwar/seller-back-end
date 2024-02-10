package com.TechTron.sellerbackend.service;

import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(int id){

        return categoryRepository.findById(id);
    }

    public boolean categoryExist(int id){
        return categoryRepository.existsById(id);
    }

    @Transactional
    public Category updateCategory(int id,Category category){

        Category outDatedCategory = categoryRepository.getReferenceById(id);

        if(categoryExist(id)){

            outDatedCategory.setName(category.getName());

            return categoryRepository.save(outDatedCategory);
        }
        return null;

    }

    @Transactional
    public void deleteCategory(int id){

        categoryRepository.deleteById(id);
    }
}
