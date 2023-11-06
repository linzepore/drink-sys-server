package com.drink_sys.controller;

import com.drink_sys.entity.Food;
import com.drink_sys.entity.User;
import com.drink_sys.mapper.FoodMapper;
import com.drink_sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FoodMapper foodMapper;
    @GetMapping("/user/get")
    public String get() {
//        User user = userMapper.selectOne(null);
//        System.out.println(user);
        Food f = foodMapper.selectById(0);
        System.out.println(f);
        return "OK的，收到了"+f;
    }

}
