package com.TechTron.sellerbackend.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_tbl")

@Getter
@Setter
@Schema(hidden = false)
public class Product {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private int product_id;

    @ManyToOne()
    @JoinColumn(name = "Seller_FK",referencedColumnName = "Seller_Id")
    private Seller seller;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Category_FK",referencedColumnName = "Category_Id")
    private Category category;

    @Column(name = "Product_Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private float price;

    @Column(name = "File_Path")
    private String filePath;

}
