package com.drink_sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("foods")
public class Food implements Cloneable {
    private int id;
    private String name;
    private double price;
    private String description;
    private String image;
    private int material;
    @TableField(exist = false)
    private int quantity;

    @Override
    public Food clone() throws CloneNotSupportedException {
        return (Food) super.clone();
    }
}
