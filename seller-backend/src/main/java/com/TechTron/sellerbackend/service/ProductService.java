package com.TechTron.sellerbackend.service;

import com.TechTron.sellerbackend.data.entity.Product;
import com.TechTron.sellerbackend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Product> getProductById(int id){

        return productRepository.findById(id);
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

}
