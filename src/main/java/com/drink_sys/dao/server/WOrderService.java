package com.drink_sys.dao.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drink_sys.entity.Order;
import com.drink_sys.mapper.FoodMapper;
import com.drink_sys.mapper.OrderMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WOrderService extends ServiceImpl<OrderMapper, Order> {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    FoodMapper foodMapper;
    public Integer[] get2MonsSell() {
        Integer[] sell_return = new Integer[2];
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Date date = new Date();
        date.setMonth(date.getMonth()-2);
        orderQueryWrapper.ge("add_date", date);
        orderQueryWrapper.eq("fid",2);
        sell_return[0]=orderMapper.selectList(orderQueryWrapper).size();
        orderQueryWrapper.clear();
        orderQueryWrapper.ge("add_date", date);
        orderQueryWrapper.eq("fid",1);
        sell_return[1]=orderMapper.selectList(orderQueryWrapper).size();
        return sell_return;
    }
    public JsonNode getTodaySell() throws JsonProcessingException {
        Integer[] sell_return = new Integer[2];
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        orderQueryWrapper.ge("add_date", date).eq("fid",2);
        sell_return[0]=orderMapper.selectList(orderQueryWrapper).size();
        orderQueryWrapper.clear();
        orderQueryWrapper.ge("add_date", date).eq("fid",1);
        sell_return[1]=orderMapper.selectList(orderQueryWrapper).size();

        String origin_tree = "{\"cafe\":"+sell_return[0]+", \"milk\":"+sell_return[1]+"}";
        return new ObjectMapper().readTree(origin_tree);
    }
    public JsonNode getEarning5Day() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date date = calendar.getTime();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();	// SELECT MIN(order_total_account) from orders GROUP BY order_code
        orderQueryWrapper.select("MIN(order_total_account) AS order_total_account").ge("add_date", date)
                .groupBy("order_code");
        List<Map<String, Object>> orderList = orderMapper.selectMaps(orderQueryWrapper);
        BigDecimal sum = new BigDecimal(0);
        for(Map<String, Object> order : orderList) {
            sum = sum.add((BigDecimal) order.get("order_total_account"));
        }
        // String origin_tree = "{\"sum\":"+sum+", \"date\":\""+date.getMonth()+"-"+date.getDay()+"\"}";
        // String origin_tree = "{\"sum\":"+sum+", \"date\":["+date.getMonth()+","+date.getDay()+"]}";
        String origin_tree = "{\"sum\":"+sum+", \"date\":"+date.getTime()+"}";
        System.out.println(date);
        System.out.println(origin_tree);
        return new ObjectMapper().readTree(origin_tree);
    }
    /*public JsonNode getSell5Day() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date date = calendar.getTime();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.select("SUM(quantity) AS quantity", "DATE(add_date) AS add_date")
                .eq("fid","1").ge("add_date", date).groupBy("DATE(add_date)");
        List<Map<String, Object>> milkTeaQuantityMaps = orderMapper.selectMaps(orderQueryWrapper);
        orderQueryWrapper.clear();
        orderQueryWrapper.select("SUM(quantity) AS quantity", "DATE(add_date) AS add_date")
                .eq("fid","2").ge("add_date", date).groupBy("DATE(add_date)");
        List<Map<String, Object>> coffeeQuantityMap = orderMapper.selectMaps(orderQueryWrapper);

        System.out.println(milkTeaQuantityMaps+coffeeQuantityMap.toString());
        //现在拿到的不是每天的，而是近五天的
        for(Map<String, Object> milkTea : milkTeaQuantityMaps) {
            System.out.println("1"+milkTea.get("quantity"));
            return null;
        }
        return null;
    }*/
    public JsonNode getSell5Day() throws JsonProcessingException {
//        orderQueryWrapper.select("date_table.date","IFNULL(SUM(orders.quantity), 0) AS quantity");
        List<Map<String, Object>> daySellMilkTea = orderMapper.get5DaySell(1);
        List<Map<String, Object>> daySellCoffee = orderMapper.get5DaySell(2);
        String[] days = new String[5];
        BigDecimal[] qT = new BigDecimal[5];
        BigDecimal[] qC = new BigDecimal[5];
        for (int i = 0; i < daySellMilkTea.size(); i++) {
            days[i] = new SimpleDateFormat("yyyy-MM-dd").format((Date) daySellCoffee.get(i).get("date"));
            qT[i] = (BigDecimal) daySellMilkTea.get(i).get("quantity");
        }
        for (int i = 0; i < daySellCoffee.size(); i++) {
            qC[i] = (BigDecimal) daySellCoffee.get(i).get("quantity");
        }
        String daysResult = Arrays.asList(days).stream().map((s)-> "\"" + s + "\"").collect(Collectors.joining(", "));
        String origin_tree = "{\"cafe\":"+ Arrays.toString(qC) +", \"milk\":"+ Arrays.toString(qT) +", \"date\":["+ daysResult +"]}";
        return new ObjectMapper().readTree(origin_tree);
    }
    public Order getOrderById(String oid) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_code", oid);
        return orderMapper.selectOne(orderQueryWrapper);
    }
    public int deleteOrder(String order_id) {
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("order_code", order_id);
        return orderMapper.delete(orderUpdateWrapper);
    }
    public int updateState(String order_id, String order_status) {
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.eq("order_code", order_id);
        orderUpdateWrapper.set("order_status", order_status);
        return orderMapper.update(orderUpdateWrapper);
    }
    public Page<Order> getOrderW(int pageNum, int pageSize) {
        Page<Order> page = new Page<>(pageNum -1, pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_status", 0);
        return this.page(page,orderQueryWrapper);
    }
    public Page<Order> getOrderY(int pageNum, int pageSize) {
        Page<Order> page = new Page<>(pageNum -1, pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_status", 1);
        return this.page(page,orderQueryWrapper);
    }
}
