package com.drink_sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.drink_sys.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT date_table.date, IFNULL(SUM(orders.quantity), 0) AS quantity\n" +
            "FROM (\n" +
            "  SELECT CURDATE() - INTERVAL 4 DAY AS date\n" +
            "  UNION ALL SELECT CURDATE() - INTERVAL 3 DAY\n" +
            "  UNION ALL SELECT CURDATE() - INTERVAL 2 DAY\n" +
            "  UNION ALL SELECT CURDATE() - INTERVAL 1 DAY\n" +
            "  UNION ALL SELECT CURDATE()\n" +
            ") AS date_table\n" +
            "LEFT JOIN orders ON DATE(orders.add_date) = date_table.date AND  orders.fid = #{fid}\n" +
            "GROUP BY date_table.date")
    List<Map<String, Object>> get5DaySell(int fid);

    /*@Select("SELECT order_code,order_status,open_id,order_total_account,quantity,fid,add_date,deal_date,reward_date" +
            "FROM orders WHERE (order_status = #{status}) LIMIT #{page.size}")
    @Result()
    Page<Order> getOrderPageByStatus(Page page, int status);*/
}
