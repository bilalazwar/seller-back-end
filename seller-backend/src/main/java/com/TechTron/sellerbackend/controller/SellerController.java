package com.TechTron.sellerbackend.controller;

import com.TechTron.sellerbackend.data.entity.Seller;
import com.TechTron.sellerbackend.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Seller Controller",description = "Manages Sellers")
@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Operation(summary = "Create new Seller", description = "Creates and returns the new Seller")
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller){

        Seller seller1= sellerService.createSeller(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(seller1);
    }

    @Operation(summary = "Get Seller by Id", description = "Returns the Seller related to the ID ")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Seller>> getSellerById(@PathVariable int id){

        Optional<Seller> seller1 = sellerService.getSellerById(id);

        if(seller1.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(seller1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "List of all Sellers", description = "Returns all the Sellers ")
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(){

        List<Seller> sellers = sellerService.getAllSeller();

        return ResponseEntity.status(HttpStatus.OK).body(sellers);
    }

    @Operation(summary = "Update Seller by Id", description = "Updates & returns the Seller related to the Id")
    @PatchMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable int id, @RequestBody Seller seller){

        if(sellerService.sellerExist(id)){
            Seller seller1= sellerService.updateSeller(id,seller);

            return ResponseEntity.status(HttpStatus.OK).body(seller1);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

}
