package com.TechTron.sellerbackend.service;

import com.TechTron.sellerbackend.data.entity.Seller;
import com.TechTron.sellerbackend.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public Seller createSeller(Seller seller){

        return sellerRepository.save(seller);
    }

    public Optional<Seller> getSellerById(int id){

        return sellerRepository.findById(id);
    }
    public boolean sellerExist(int id){

        return sellerRepository.existsById(id);
    }

    public List<Seller> getAllSeller(){

        return sellerRepository.findAll();
    }

    @Transactional
    public Seller updateSeller(int id, Seller seller){

        if(sellerExist(id)){

            Seller outDatedSeller = sellerRepository.getReferenceById(id);    // creates a proxy obj.but it doesn't actually fetch the data from the database until you try to access its properties or methods.
            Seller UpdatingSeller = mergeSellerData(outDatedSeller,seller);

            return sellerRepository.save(UpdatingSeller);
        }
        else {
            return null;
        }
    }

    @Transactional
    public Seller mergeSellerData(Seller existingSeller, Seller updatedSeller) {

        if(updatedSeller.getName() != null){

            existingSeller.setName(updatedSeller.getName());
        }
        else if(updatedSeller.getEmail() != null){

            existingSeller.setEmail(updatedSeller.getEmail());
        }
        else if(updatedSeller.getMobileNumber() !=0){

            existingSeller.setMobileNumber(updatedSeller.getMobileNumber());
        }
        else if(updatedSeller.getAddress() != null){

            existingSeller.setAddress(updatedSeller.getAddress());
        }
        else if(updatedSeller.getBusinessName() != null){

            existingSeller.setBusinessName(updatedSeller.getBusinessName());
        }
        return existingSeller;
    }




}
