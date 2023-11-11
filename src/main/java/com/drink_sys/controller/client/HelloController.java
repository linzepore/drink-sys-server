package com.drink_sys.controller.client;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "后端服务测试")
@RestController
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "DrinkSys Server is running...!";
    }
}
