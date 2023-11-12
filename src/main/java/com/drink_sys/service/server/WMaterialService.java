package com.drink_sys.service.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drink_sys.entity.Material;
import com.drink_sys.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WMaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    public List<Material> getMaterial() {
        List<Material> materials = materialMapper.selectList(new QueryWrapper<>());
        System.out.println(materials);
        return materials;
    }
    public int delMaterial(Integer material_id) {
        UpdateWrapper<Material> materialUpdateWrapper = new UpdateWrapper<>();
        System.out.println(material_id);
        materialUpdateWrapper.eq("material_id", material_id);
        return materialMapper.delete(materialUpdateWrapper);
    }
    public int updateMaterial(Material material) {
        UpdateWrapper<Material> materialUpdateWrapper = new UpdateWrapper<>();
        materialUpdateWrapper.eq("material_id", material.getMaterialId()).set("material_name", material.getMaterialName())
                .set("material_full", material.getMaterialFull()).set("material_remain", material.getMaterialRemain());
        return materialMapper.update(materialUpdateWrapper);
    }
    public int modifyMaterialRemain(int material_id, BigDecimal material_consume) {
        QueryWrapper<Material> materialQueryWrapper = new QueryWrapper<>();
        materialQueryWrapper.eq("material_id", material_id);
        Material material = materialMapper.selectOne(materialQueryWrapper);
        UpdateWrapper<Material> materialUpdateWrapper = new UpdateWrapper<>();
        materialUpdateWrapper.eq("material_id", material_id)
                .set("material_remain", material.getMaterialRemain().subtract(material_consume));
        return materialMapper.update(materialUpdateWrapper);
    }
}
