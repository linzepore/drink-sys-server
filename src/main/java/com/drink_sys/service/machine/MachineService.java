package com.drink_sys.service.machine;

import com.drink_sys.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
    @Autowired
    private OrderMapper orderMapper;
    public boolean pushNewOrder(String orderCode, String position) {

        return false;
    }
}
