package com.TechTron.sellerbackend.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seller_tbl")

@Getter
@Setter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Seller_Id")
    private int sellerId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Mobile_Number")
    private long mobileNumber;

    @Column(name = "Address")
    private String address;

    @Column(name = "business_name")
    private String businessName;

    //Uni-Directional

//    @JsonBackReference
//    @OneToMany(mappedBy = "seller")
//    private List<Product> product;

}
