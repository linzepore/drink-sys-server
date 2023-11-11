package com.drink_sys.controller.server;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.drink_sys.dao.server.WFoodService;
import com.drink_sys.entity.Food;
import com.drink_sys.entity.Msg;
import com.drink_sys.mapper.FoodMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/web/drink")
public class FoodServerController {
    @Autowired
    private WFoodService wFoodService;
    @GetMapping("/queryDrink")
    public Msg<Food> queryDrink(int queryNum) {
        Msg<Food> listMsg = new Msg<>();
        try {
            Food foodList = wFoodService.queryDrink(queryNum);
            if (foodList!=null) {
                listMsg.beSucceed(foodList, "查询成功");
            }
        } catch (Exception e) {
            listMsg.beFailed(null,"查询失败，发生了异常：" + e);
        }
        return listMsg;
    }
    @GetMapping("/getDrink")
    public IPage<Food> getDrink(int pageNum, int pageSize) {
        try {
            return wFoodService.getDrink(pageNum, pageSize);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/updateDrink")
    public Msg<String> updateDrink(@RequestBody JsonNode jsonNode) {
        Msg<String> stringMsg = new Msg<>();
        try {
            Food food = new Food();
            food.setId(jsonNode.get("drink_id").asInt());
            food.setName(jsonNode.get("drink_name").asText());
            food.setImage(jsonNode.get("drink_photo").asText());
            food.setPrice(jsonNode.get("drink_price").asDouble());
            food.setMaterial(jsonNode.get("drink_material").asInt());

            int updated = wFoodService.updateDrink(food);
            if (updated > 0) {
                stringMsg.beSucceed("success","更新了"+updated+"条记录");
            } else {
                stringMsg.beFailed("fail", "更新失败");
            }
        } catch (Exception e) {
            stringMsg.beFailed("fail","查询失败，发生了异常：" + e);
        }
        return stringMsg;
    }

    @DeleteMapping("/deleteDrink")
    public Msg<String> deleteDrink(int drink_id) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int deleted = wFoodService.deleteDrink(drink_id);
            if (deleted > 0) {
                stringMsg.beSucceed("success", "成功删除了" + deleted + "条记录");
            } else stringMsg.beFailed("fail", "删除失败");
        } catch (Exception e) {
            stringMsg.beFailed("fail","删除失败，发生了异常："+e);
        }
        return stringMsg;
    }
}
