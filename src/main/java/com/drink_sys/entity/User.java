package com.drink_sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user")
public class User {
    private int uid;
    private String name;
    private Date birthdate;
    private String location;
    private String mobile;
    private String openId;

    private String nickname;
    private String avatarUrl;
}
