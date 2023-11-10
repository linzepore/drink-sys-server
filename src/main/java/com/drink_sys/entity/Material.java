package com.drink_sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("materials")
public class Material {
    @JsonProperty("material_id")
    @TableField("material_id")
    private int materialId;
    @JsonProperty("material_name")
    @TableField("material_name")
    private String materialName;
    @JsonProperty("material_remain")
    @TableField("material_remain")
    private BigDecimal materialRemain;
    @JsonProperty("material_full")
    @TableField("material_full")
    private BigDecimal materialFull;
}
