package com.example.Order.Service.Listners;

import com.example.Order.Service.DTO.OrderDTO;
import com.example.Order.Service.Models.Orders;
import com.example.Order.Service.Services.orderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private orderService orderservice;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(OrderDTO orderDTO) {
        System.out.println("Received Order from RabbitMQ: " + orderDTO);
        Orders order = new Orders(orderDTO);
        orderservice.createOrder(order);
    }
}
