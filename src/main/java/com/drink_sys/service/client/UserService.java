package com.drink_sys.service.client;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.drink_sys.entity.User;
import com.drink_sys.mapper.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Value("wxddf5165783eb9495")
    private String appId;
    @Value("e912ee5abfa8ecd1007b25a338b42b60")
    private String secret;
    @Autowired
    UserMapper userMapper;
    public boolean isUserExist(String openId) {
        if(openId != null) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("open_id", openId);
            List<User> users = userMapper.selectList(userQueryWrapper);
            return users.size() > 0;
        }
        return false;
    }
    public int addUser(User user) {
        return userMapper.insert(user);
    }
    public int updateUser(User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("name", user.getName()).set("birthdate",user.getBirthdate())
                        .set("location", user.getLocation()).set("mobile", user.getMobile());
        userUpdateWrapper.eq("open_id", user.getOpenId());
        return userMapper.update(userUpdateWrapper);
    }
    public List<User> getUser(String openId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("open_id", openId);
        return userMapper.selectList(userQueryWrapper);
    }
    public int changUserSimply(JsonNode jsonNode) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("avatar_url", jsonNode.get("avatarUrl").asText());
        userUpdateWrapper.set("nickName", jsonNode.get("nickName").asText());
        userUpdateWrapper.eq("open_id", jsonNode.get("openId").asText());
        return userMapper.update(userUpdateWrapper);
    }
    public JsonNode getUserOpenId(String code) throws JsonProcessingException {
        String authUrl="https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
        authUrl = authUrl + "&appid=" + appId + "&secret=" + secret + "&js_code=" + code;
        String result = HttpUtil.get(authUrl);
        return new ObjectMapper().readTree(result);
    }
}
