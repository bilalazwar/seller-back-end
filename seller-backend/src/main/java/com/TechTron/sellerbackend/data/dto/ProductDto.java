package com.TechTron.sellerbackend.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductDto {

    private int product_id;
    private int sellerIdFk;
    private int categoryIdFk;
    private String name;
    private String description;
    private float price;
    private String filePath;
}
