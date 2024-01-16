package com.drink_sys.service.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drink_sys.entity.User;
import com.drink_sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WUserService {
    @Autowired
    UserMapper userMapper;
    public Boolean verifyUser(String username, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        User user = userMapper.selectOne(userQueryWrapper);
        return user.getOpenId().equals(password);
    }
    public Boolean hasUser(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        return userMapper.selectList(userQueryWrapper).size()>0;
    }
    public User getUserByName(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", username);
        return userMapper.selectOne(userQueryWrapper);
    }
}
