package com.drink_sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

@Data
@TableName("orders")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    @TableId(type = IdType.ASSIGN_UUID)
    private String orderCode;
    private int orderStatus;
    private String openId;
    private BigDecimal orderTotalAccount;
    @JsonIgnore
    private String quantity;
    @JsonIgnore
    private String fid;
    @TableField(exist = false)
    private List<Food> foods= new ArrayList<>();

    public boolean addFood(Food food) {
        if (food != null && this.foods != null) {
            this.foods.add(food);
            return true;
        } else if (this.foods == null) {
            this.foods = Collections.singletonList(food);
            return true;
        }
        return false;
    }
}
