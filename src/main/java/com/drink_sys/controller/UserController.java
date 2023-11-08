package com.drink_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drink_sys.entity.Msg;
import com.drink_sys.entity.User;
import com.drink_sys.mapper.UserMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @PostMapping("/userDetailInfoAdd")
    public Msg<String> userAdd(@RequestBody User user) {
        Msg<String> stringMsg = new Msg<>();
        System.out.println(user);
        if (user != null) {
            userMapper.insert(user);
            stringMsg.setMsg("添加成功");
            stringMsg.setCode("1");
            stringMsg.setData("success");
        } else {
            stringMsg.setMsg("添加失败,用户为空");
            stringMsg.setCode("1");
            stringMsg.setData("fail");
        }
        return stringMsg;
    }

    @PostMapping("getUserDetailInfo")
    public Msg<List<User>> userGet(String openId) {
        Msg<List<User>> userMsg = new Msg<>();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("open_id", openId);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        if(userList.size() > 0) {
            userMsg.setData(userList);
            userMsg.setCode("1");
            userMsg.setMsg("查询到"+ userList.size()+"条用户记录");
        } else {
            userMsg.setData(null);
            userMsg.setCode("0");
            userMsg.setMsg("查无记录");
        }
        return userMsg;
    }

    @PutMapping("/modifyUserWxInfo")
    public Msg<String> changeBasicInfo(@RequestBody JsonNode jsonNode) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("avatar_url", jsonNode.get("avatarUrl").asText());
        userUpdateWrapper.set("nickName", jsonNode.get("nickName").asText());
        userUpdateWrapper.eq("open_id", jsonNode.get("openId").asText());
        int update_line = userMapper.update(userUpdateWrapper);
        Msg<String> stringMsg = new Msg<>();
        if(update_line > 0) {
            stringMsg.setData("success");
            stringMsg.setMsg("修改了"+update_line+"条数据");
            stringMsg.setCode("1");
        } else {
            stringMsg.setData("fail");
            stringMsg.setMsg("修改出错,建议检查openId");
            stringMsg.setCode("0");
        }
        return stringMsg;
    }
}
