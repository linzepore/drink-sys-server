package com.drink_sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("foods")
public class Food {
    private int id;
    private String name;
    private double price;
    private String description;
    private String image;
    private int quantity;
}
