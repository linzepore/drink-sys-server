package com.drink_sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int uid;
    private String name;
    private Date birthdate;
    private String location;
    private String mobile;
}
