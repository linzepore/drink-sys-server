package com.drink_sys.controller.client;

import cn.hutool.json.JSON;
import com.drink_sys.controller.machine.WebSocketController;
import com.drink_sys.service.client.OrderService;
import com.drink_sys.entity.Msg;
import com.drink_sys.entity.Order;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "C端订单管理")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WebSocketController webSocketController;

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
                String orderId = new ObjectMapper().readTree(orderString).get("order").get("orderId").asText();
                // 发送新订单消息
                webSocketController.notifyNewOrder(orderId);
                return stringMsg.beSucceed("success", "添加成功");
            } else {
                return stringMsg.beSucceed("fail", "添加失败,未知错误");
            }
        } catch (Exception e) {
            return new Msg<String>().beSucceed("fail", "添加失败,出现了异常: " + e);
        }
    }
    @PostMapping ("/orderCancel")
    public Msg<String> cancelOrder(String orderCode) {
        try{
            int deleted = orderService.cancelOrder(orderCode);
            Msg<String> stringMsg = new Msg<>();
            if(deleted > 0 ) {
                return stringMsg.beSucceed("success", "删除成功");
            } else {
                return stringMsg.beSucceed("fail", "删除失败,未知错误");
            }
        } catch (Exception e) {
            return new Msg<String>().beSucceed("fail", "删除失败,出现了异常: " + e);
        }
    }
}
