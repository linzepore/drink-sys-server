package com.drink_sys.controller.client;

import com.drink_sys.dao.client.OrderService;
import com.drink_sys.entity.Msg;
import com.drink_sys.entity.Order;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "C端订单管理")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orderGet")
    public Msg<List<Order>> getOrder(String openId){
        try{
            List<Order> orders = this.orderService.getOrderByOpenId(openId);
            return new Msg<List<Order>>().beSucceed(orders, "查询到" + orders.size() + "个订单");
        } catch (Exception e) {
            return new Msg<List<Order>>().beFailed(null, "查询失败,出现了异常: " + e);
        }
    }
    @PostMapping("/orderAdd")
    public Msg<String> addOrder(@RequestBody String orderString) {
        try{
            Boolean added = orderService.addOrder(orderString);
            Msg<String> stringMsg = new Msg<>();
            if(added) {
                return stringMsg.beSucceed("success", "添加成功");
            } else {
                return stringMsg.beSucceed("fail", "添加失败,位置错误");
            }
        } catch (Exception e) {
            return new Msg<String>().beSucceed("fail", "添加失败,出现了异常: " + e);
        }
    }
}
