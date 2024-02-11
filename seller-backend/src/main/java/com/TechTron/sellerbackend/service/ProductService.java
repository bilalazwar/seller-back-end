package com.TechTron.sellerbackend.service;

import com.TechTron.sellerbackend.data.dto.ProductDto;
import com.TechTron.sellerbackend.data.entity.Product;
import com.TechTron.sellerbackend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){

        return productRepository.save(product);
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

    public List<Product> getAllProduct(){

        return productRepository.findAll();
    }

    @Transactional
    public Product updateProduct(int id, Product product){

        if(sellerExist(id)){

            Product outDatedProduct = productRepository.getReferenceById(id);    // creates a proxy obj.but it doesn't actually fetch the data from the database until you try to access its properties or methods.
            Product updatingProduct = mergeProductData(outDatedProduct,product);

            return productRepository.save(updatingProduct);
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

//    public ProductDto convertEntityToProductDto(Product product){
//
//        ProductDto productDto = new ProductDto();
//
//        productDto.setProduct_id(product.getProduct_id());
//        productDto.setName(product.getName());
//        productDto.setDescription(product.getDescription());
//        productDto.setPrice(product.getPrice());
//        productDto.setFilePath(product.getFilePath());
//        productDto.setCategoryIdFk(product.getSeller().getSellerId());
//        productDto.setSellerIdFk(product.getCategory().getCategory_id());
//
//        return productDto;
//    }

    public ProductDto convertEntityToProductDto(Product product){

        ProductDto productDto = new ProductDto();

        productDto.setProduct_id(product.getProduct_id());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setFilePath(product.getFilePath());
        productDto.setCategoryIdFk(product.getSeller().getSellerId());
        productDto.setSellerIdFk(product.getCategory().getCategory_id());

        return productDto;
    }

    public List<ProductDto> convertEntityToProductDtoList(List<Product> products){

        List<ProductDto> productDtos = new ArrayList<>();

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
