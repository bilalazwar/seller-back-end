package com.TechTron.sellerbackend.controller;

import com.TechTron.sellerbackend.data.dto.ProductDto;
import com.TechTron.sellerbackend.data.entity.Product;
import com.TechTron.sellerbackend.service.ImageService;
import com.TechTron.sellerbackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Product Controller",description = "Manages Products")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;


    //in Post-Man form-data send
    @Operation(summary = "Create New Product", description = "create a new product with Product image. Here we are using ProductDto for returning and incoming request")
    @PostMapping(consumes = "multipart/form-data")//This indicates that the request body contains multiple parts, each with its own content type and boundaries separating them.
    public ProductDto createProduct(@RequestPart("image") MultipartFile imageFile, @RequestPart("product") ProductDto productDto){

        String filePath = imageService.uploadFile(imageFile); // Handle file upload and generate filepath
        productDto.setFilePath(filePath);
        return productService.createProduct(productDto);
    }


    @Operation(summary = "List of All Products", description = "Return all the Product details , also the product image Url is included")
    @GetMapping
    public List<ProductDto> getAllProducts(){

        return productService.getAllProduct();
    }

    //get all seller product by ID

    @Operation(summary = "Get Product by Id", description = "Returns the product related to the ID")
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable int id){

        return productService.getProductById(id);
    }

    @Operation(summary = "Update Product by Id", description = "Updates & returns the Product related to the Id")
    @PatchMapping("/{id}")
    public ProductDto updateProductById(@PathVariable int id,@RequestBody ProductDto productDto){

        return productService.updateProduct(id,productDto);
    }

    @Operation(summary = "Delete Product by Id", description = "Deletes the Product related to the Id")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable int id){

        productService.deleteProduct(id);
    }
}
