package com.drink_sys.controller.client;

import com.drink_sys.service.client.UserService;
import com.drink_sys.entity.Msg;
import com.drink_sys.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "C端用户管理")
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/userAdd")
    public Msg<String> userAdd(String openId) {
        Msg<String> stringMsg = new Msg<>();
        try {
            boolean userExist = this.userService.isUserExist(openId);
            if (!userExist) {
                User user = new User();
                user.setOpenId(openId);
                int added = this.userService.addUser(user);
                if (added > 0 ) return stringMsg.beSucceed("success", "添加成功");
                else return stringMsg.beSucceed("fail", "添加失败");
            }
        } catch (Exception e) {
            return new Msg<String>().beFailed("fail", "添加失败,出现了异常: " + e);
        }
        return stringMsg.beFailed("fail","添加失败,该用户可能已存在");
    }
    @PostMapping("/userDetailInfoUpdate")
    public Msg<String> userDetailInfoUpdate(@RequestBody User user) {
        Msg<String> stringMsg = new Msg<>();
        try {

            System.out.println(user);
            if (user != null) {
                if (userService.updateUser(user) > 0) stringMsg.beSucceed("success", "更新成功");
                else stringMsg.beFailed("fail", "更新失败,无法插入数据");
            } else stringMsg.beFailed("fail", "更新失败,用户为空");

        } catch (Exception e) {
            return new Msg<String>().beFailed("fail", "更新失败,出现了异常: " + e);
        }
        return stringMsg;
    }

    @PostMapping("getUserDetailInfo")
    public Msg<List<User>> userGet(String openId) {
        Msg<List<User>> userMsg = new Msg<>();
        try {
            List<User> userList = userService.getUser(openId);
            if (userList.size() > 0) {
                userMsg.beSucceed(userList, "查询到" + userList.size() + "条用户记录");
            } else {
                userMsg.beSucceed(null, "查无记录");
            }
        } catch (Exception e) {
            return userMsg.beFailed(null, "查询失败,出现了异常: " + e);
        }
        return userMsg;
    }

    @PutMapping("/modifyUserWxInfo")
    public Msg<String> changeBasicInfo(@RequestBody JsonNode jsonNode) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int update_line = userService.changUserSimply(jsonNode);
            if (update_line > 0) {
                stringMsg.beSucceed("success", "修改了" + update_line + "条数据");
            } else {
                stringMsg.beFailed("fail", "修改出错,建议检查openId");
            }
        } catch (Exception e) {
            return new Msg<String>().beFailed("fail", "修改失败,出现了异常: " + e);
        }
        return stringMsg;
    }

    @GetMapping("/getUserLoginStatus")
    public Msg<JsonNode> addUserOpenId(String code) {
        try{
            JsonNode jsonNode = userService.getUserOpenId(code);
            Msg<JsonNode> jsonNodeMsg = new Msg<>();
            if (jsonNode.has("openid")) {
                jsonNodeMsg.beSucceed(jsonNode, "获取成功");
            } else jsonNodeMsg.beFailed(jsonNode, "获取失败");
            return jsonNodeMsg;
        } catch (Exception e) {
            return new Msg<JsonNode>().beFailed(null, "获取失败,出现了异常: " + e);
        }
    }
}
