package com.TechTron.sellerbackend.controller;

import com.TechTron.sellerbackend.data.entity.Product;
import com.TechTron.sellerbackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product){

        return productService.createProduct(product);
//        return null;
    }
    @GetMapping
    public List<Product> getAllProducts(){

        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable int id){
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}")
    public Product updateProductById(@PathVariable int id,@RequestBody Product product){

        return productService.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable int id){

        productService.deleteProduct(id);
    }
}
