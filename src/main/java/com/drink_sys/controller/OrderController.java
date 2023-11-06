package com.drink_sys.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @PostMapping("/getOrder")
    public String getOrder(String openid){
        return "user";
    }
}
