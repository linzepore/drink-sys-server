package com.drink_sys.controller.server;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drink_sys.service.server.WOrderService;
import com.drink_sys.entity.Msg;
import com.drink_sys.entity.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "B端订单管理")
@RequestMapping("/web/order")
public class OrderServerController {

    @Autowired
    WOrderService wOrderService;

    @GetMapping("/sellDrinksMonths")
    public Msg<Integer[]> getSellDrinksMonths() {
        Msg<Integer[]> integerMsg = new Msg<>();
        try {
            Integer[] sell_return = wOrderService.get2MonsSell();
            integerMsg.beSucceed(sell_return, "查询成功");
        } catch (Exception e) {
            integerMsg.beFailed(null, "查询失败，发生了异常：" + e);
        }
        return integerMsg;
    }

    @GetMapping("/sellDrinksToday")
    public Msg<JsonNode> getSellToday() throws JsonProcessingException {
        Msg<JsonNode> jsonNodeMsg = new Msg<>();
        try {
            JsonNode result = this.wOrderService.getTodaySell();
            if (result.has("cafe")) {
                jsonNodeMsg.beSucceed(result, "查询成功");
            } else jsonNodeMsg.beFailed(null, "查询失败");
        } catch (Exception e) {
            jsonNodeMsg.beFailed(null, "查询失败，发生了异常：" + e);
        }
        return jsonNodeMsg;
    }


    @GetMapping("/sellDrinksMoney")
    public Msg<JsonNode> getEarning5Day() throws JsonProcessingException {
        try{
            JsonNode earning5Day = this.wOrderService.getEarning5Day();
            return new Msg<JsonNode>().beSucceed(earning5Day,"查询成功");
        } catch (Exception e) {
            return new Msg<JsonNode>().beFailed(null,"查询失败, 发生了异常: "+e);
        }
    }

    @GetMapping("/sellDrinksFiveDays")
    public Msg<JsonNode> getSellDrinksFiveDays() throws JsonProcessingException {
        Msg<JsonNode> jsonNodeMsg = new Msg<>();
        try {
            JsonNode sell5Day = wOrderService.getSell5Day();
            if (sell5Day == null) {
                jsonNodeMsg.beFailed(null, "获取失败");
            } else {
                jsonNodeMsg.beSucceed(sell5Day, "查询成功");
            }
        } catch (Exception e) {
            return new Msg<JsonNode>().beFailed(null,"查询失败, 发生了异常: "+e);
        }
        return jsonNodeMsg;
    }
    @GetMapping("/getOrderById")
    public Msg<Order> getOrderById(String queryNum) {
        Msg<Order> orderMsg = new Msg<>();
        try {
            Order order = wOrderService.getOrderById(queryNum);
            if (order != null) {
                orderMsg.beSucceed(order, "查询成功");
            } else {
                orderMsg.beFailed(null, "查无结果");
            }
        } catch (Exception e) {
            return new Msg<Order>().beFailed(null,"查询失败, 发生了异常: "+e);
        }
        return orderMsg;
    }
    @DeleteMapping("/deleteOrder")
    public Msg<String> deleteOrder(String order_id) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int deleted = wOrderService.deleteOrder(order_id);
            if (deleted > 0) {
                stringMsg.beSucceed("success", "成功删除了 " + deleted + " 条记录");
            } else stringMsg.beFailed(null, "删除失败");
        } catch (Exception e) {
            stringMsg.beFailed("fail", "更新失败，发生了异常：" + e);
        }
        return stringMsg;
    }
    @PutMapping("/updateState")
    public Msg<String> updateState(String order_id, String order_status) {
        Msg<String> stringMsg = new Msg<>();
        try {
            int updated = wOrderService.updateState(order_id, order_status);
            if (updated > 0) {
                stringMsg.beSucceed("success", "成功修改了 " + updated + " 条记录");
            } else stringMsg.beFailed(null, "删除失败");
        } catch (Exception e) {
            stringMsg.beFailed("fail", "删除失败，发生了异常：" + e);
        }
        return stringMsg;
    }
    @GetMapping("/getOrderW")
    public Page<Order> getOrderW(int pageNum, int pageSize) throws CloneNotSupportedException {
        return wOrderService.getOrderW(pageNum, pageSize);
    }
    @GetMapping("/getOrderY")
    public Page<Order> getOrderY(int pageNum, int pageSize) {
        return wOrderService.getOrderY(pageNum, pageSize);
    }
}
