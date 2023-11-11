package com.drink_sys.dao.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drink_sys.entity.Food;
import com.drink_sys.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WFoodService {
    @Autowired
    FoodMapper foodMapper;
    public Food queryDrink(int queryNum) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",queryNum);
        return foodMapper.selectOne(queryWrapper);
    }
    public IPage<Food> getDrink(int pageNum, int pageSize) {
        IPage<Food> foodPage = new Page<>(pageNum-1, pageSize);
        return this.foodMapper.selectPage(foodPage,null);
    }
    public int updateDrink(Food food) {
        UpdateWrapper<Food> foodUpdateWrapper = new UpdateWrapper<>();
        foodUpdateWrapper.eq("id", food.getId());
        return foodMapper.update(food, foodUpdateWrapper);
    }
    public int deleteDrink(int id) {
        UpdateWrapper<Food> foodUpdateWrapper = new UpdateWrapper<>();
        foodUpdateWrapper.eq("id", id);
        return foodMapper.delete(foodUpdateWrapper);
    }
}
