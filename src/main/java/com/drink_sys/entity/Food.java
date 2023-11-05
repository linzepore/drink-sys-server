package com.drink_sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private int id;
    private String name;
    private double price;
    private String desc;
    private String image;
    private int quantity;
}
