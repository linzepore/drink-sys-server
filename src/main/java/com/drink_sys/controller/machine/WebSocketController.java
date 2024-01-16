package com.drink_sys.controller.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/send/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        return message;
    }
    @MessageMapping("/new/order")
    public void notifyNewOrder(String orderId) {
        messagingTemplate.convertAndSend("/topic/newOrder", "您有新订单了: " + orderId);
    }
}
