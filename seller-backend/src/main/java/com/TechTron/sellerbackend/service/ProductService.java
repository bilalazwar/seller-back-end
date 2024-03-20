package com.TechTron.sellerbackend.service;

import com.TechTron.sellerbackend.data.dto.ProductDto;
import com.TechTron.sellerbackend.data.entity.Category;
import com.TechTron.sellerbackend.data.entity.Product;
import com.TechTron.sellerbackend.data.entity.Seller;
import com.TechTron.sellerbackend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amazonaws.services.s3.model.CryptoStorageMode.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerService sellerService;
    @Autowired
    private CategoryService categoryService;


    @Transactional
    public ProductDto createProduct(ProductDto productDto){

        Product product = convertProductDtoToEntity(productDto);
        productRepository.save(product);

        return productDto;
    }

    //get Product by category

    public ProductDto getProductById(int id){

        Optional<Product> product = productRepository.findById(id);
        ProductDto productDto = convertEntityToProductDto(product.get());

        return productDto;
    }
    public boolean sellerExist(int id){

        return productRepository.existsById(id);
    }

    //convert and send the DTO npt entity
    public List<ProductDto> getAllProduct(){

        List<Product> products = productRepository.findAll();

        return convertEntityToProductDtoList(products);
    }


    @Transactional
    public ProductDto updateProduct(int id, ProductDto productDto){

        if(sellerExist(id)){

            Product outDatedProduct = productRepository.getReferenceById(id);    // creates a proxy obj.but it doesn't actually fetch the data from the database until you try to access its properties or methods.

            Product product = convertProductDtoToEntity(productDto);
            Product updatingProduct = mergeProductData(outDatedProduct,product);
            productRepository.save(updatingProduct);

            return convertEntityToProductDto(updatingProduct);
        }
        else {
            return null;
        }
    }

    @Transactional
    public Product mergeProductData(Product existingProduct, Product updatedProduct) {

        if(updatedProduct.getName() != null){
            existingProduct.setName(updatedProduct.getName());
        }
        else if(updatedProduct.getDescription() != null){
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        else if(updatedProduct.getPrice() != 0){
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        else if(updatedProduct.getFilePath() != null){
            existingProduct.setFilePath(updatedProduct.getFilePath());
        }

        return existingProduct;
    }
    @Transactional
    public void deleteProduct (int id){
         productRepository.deleteById(id);
    }

public Product convertProductDtoToEntity(ProductDto productDto) {

    Product product = new Product();

    if (productDto.getProduct_id() != 0) {
        product.setProduct_id(productDto.getProduct_id());
    }
    if (productDto.getName() != null) {
        product.setName(productDto.getName());
    }
    if (productDto.getDescription() != null) {  // Added check for description
        product.setDescription(productDto.getDescription());
    }
    if (productDto.getPrice() != 0) {
        product.setPrice(productDto.getPrice());
    }
    if (productDto.getFilePath() != null) {
        product.setFilePath(productDto.getFilePath());
    }
    Optional<Seller> seller = sellerService.getSellerById(productDto.getSellerIdFk());
    if (seller.isPresent()) {
        product.setSeller(seller.get());
    }
    Optional<Category> category = categoryService.getCategoryById(productDto.getCategoryIdFk());
    if (category.isPresent()) {
        product.setCategory(category.get());
    }

    return product;
}


    public ProductDto convertEntityToProductDto(Product product){

        ProductDto productDto = new ProductDto();

        if(product.getProduct_id() != 0){
            productDto.setProduct_id(product.getProduct_id());
        }
        if(product.getName() != null){
            productDto.setName(product.getName());
        }
        if(product.getDescription() != null){
            productDto.setDescription(product.getDescription());
        }
        if(product.getPrice() != 0){
            productDto.setPrice(product.getPrice());
        }
        if(product.getFilePath() != null){
            productDto.setFilePath(product.getFilePath());
        }
        if(product.getCategory() != null){
            productDto.setCategoryIdFk(product.getSeller().getSellerId());
        }
        if(product.getSeller() != null){
            productDto.setSellerIdFk(product.getCategory().getCategory_id());
        }

        return productDto;
    }

    public List<ProductDto> convertEntityToProductDtoList(List<Product> products){

        List<ProductDto> productDtos = new ArrayList<>();

//      Iterates through each element in the products list,
        for (Product product : products){

            ProductDto productDto = new ProductDto();

            productDto.setProduct_id(product.getProduct_id());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setFilePath(product.getFilePath());
            productDto.setPrice(product.getPrice());
            productDto.setCategoryIdFk(product.getSeller().getSellerId());
            productDto.setSellerIdFk(product.getCategory().getCategory_id());

            productDtos.add(productDto);
        }
        return productDtos;
    }

    // return ListProductDto and normal ProductDto

}
