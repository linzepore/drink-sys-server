package com.drink_sys.controller.server;

import com.drink_sys.dao.server.WMaterialService;
import com.drink_sys.entity.Material;
import com.drink_sys.entity.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/web/material")
@RestController
public class MaterialServerController {
    @Autowired
    WMaterialService wMaterialService;
    @GetMapping("/getMaterial")
    public Msg<List<Material>> getMaterial() {
        Msg<List<Material>> listMsg = new Msg<>();
        try {
            List<Material> material = wMaterialService.getMaterial();
            System.out.println(material);
            if (material.size() > 0) {
                listMsg.beSucceed(material, "查询到" + material.size() + "条记录");
            } else {
                listMsg.beFailed(null, "查询失败");
            }
        } catch (Exception e) {
            listMsg.beFailed(null, "查询失败，发生了异常：" + e);
        }
        return listMsg;
    }
    @DeleteMapping("/deleteMaterial")
    public Msg<String> deleteMaterial(@RequestParam("material_id") Integer mid) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int i = wMaterialService.delMaterial(mid);
            if (i > 0) {
                stringMsg.beSucceed("success", "成功删除了" + i + "条记录");
            } else {
                stringMsg.beFailed("fail", "删除失败");
            }
        } catch (Exception e) {
            stringMsg.beFailed("fail", "删除失败，发生了异常：" + e);
        }
        return stringMsg;
    }
    @PutMapping("/updateMaterial")
    public Msg<String> updateMaterial(@RequestBody Material material) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int i = wMaterialService.updateMaterial(material);
            if (i > 0) {
                stringMsg.beSucceed("success", "成功更新了" + i + "条记录");
            } else {
                stringMsg.beFailed("fail", "更新失败");
            }
        } catch (Exception e) {
            stringMsg.beFailed("fail", "更新失败，发生了异常：" + e);
        }
        return stringMsg;
    }
    @PatchMapping("updateMaterialB")
    public Msg<String> modifyMaterialRemain(BigDecimal material_consume, int material_id) {
        Msg<String> materialMsg = new Msg<>();
        try {
            int i = wMaterialService.modifyMaterialRemain(material_id, material_consume);
            if (i > 0) {
                materialMsg.beSucceed("success", "成功更新了" + i + "条记录");
            } else {
                materialMsg.beFailed("fail", "更新失败");
            }
        } catch (Exception e) {
            materialMsg.beFailed("fail", "更新失败，发生了异常：" + e);
        }
        return materialMsg;
    }
}
