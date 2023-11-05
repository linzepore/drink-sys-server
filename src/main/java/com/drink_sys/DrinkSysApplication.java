package com.drink_sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.drink_sys.mapper")
public class DrinkSysApplication {
	public static void main(String[] args) {
		SpringApplication.run(DrinkSysApplication.class, args);
	}

}
