package com.drink_sys.dao.client;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.drink_sys.entity.Food;
import com.drink_sys.entity.Order;
import com.drink_sys.mapper.FoodMapper;
import com.drink_sys.mapper.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {
    private List<Food> foods;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private OrderMapper orderMapper;
    public List<Order> getOrderByOpenId(String openId) {
        this.foods = foodMapper.selectList(null);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 等同于
        // SELECT order_code,GROUP_CONCAT(fid) AS fid1,GROUP_CONCAT(quantity) AS quantity1,open_id,
        // MIN(order_total_account) FROM orders WHERE (open_id = 121) GROUP BY order_code
        queryWrapper.select("order_code,MIN(order_status) AS order_status,GROUP_CONCAT(fid) AS fid," +
                "GROUP_CONCAT(quantity) AS quantity, open_id, MIN(order_total_account) AS order_total_account");
        queryWrapper.eq("open_id", openId).groupBy("order_code");
        List<Order> orders = orderMapper.selectList(queryWrapper);
        // 取出当前的订单中的餐品id，存入要返回的订单中
        for (int i = 0; i < orders.size(); i++) {
            String[] order_foods_id = orders.get(i).getFid().split(",");
            String[] order_quantity = orders.get(i).getQuantity().split(",");
            System.out.println(this.foods);
            for (int j = 0; j < order_foods_id.length; j++) {
                Food food = foods.get(Integer.parseInt(order_foods_id[j]));
                food.setQuantity(Integer.parseInt(order_quantity[j]));
                orders.get(i).addFood(food);
            }
        }
        return orders;
    }

    public Boolean addOrder(String orderString) throws JsonProcessingException {
        this.foods = foodMapper.selectList(null);
        System.out.println(orderString);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode orderNode = objectMapper.readTree(orderString);
        JsonNode orderFoods = orderNode.get("order").get("foods");
        JsonNode order = orderNode.get("order");
        if(orderFoods.size() < 0) {
            return false;
        }
        UUID uuid = UUID.randomUUID();
        for (int i = 0; i < orderFoods.size(); i++) {
            if (!Objects.equals(orderFoods.get(i).get("id").asText(), "0")) {
                orderMapper.insert(new Order(uuid.toString(), 0,
                        order.get("openId").asText(), order.get("orderTotalAccount").decimalValue(),
                        orderFoods.get(i).get("quantity").asText(), orderFoods.get(i).get("id").asText(),
                        new Date(), null, null, this.foods));
            }
        }
        return true;
    }
}
