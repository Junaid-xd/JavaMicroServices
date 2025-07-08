package com.example.User.Service.services;

import com.example.User.Service.DTO.OrderDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public void send(OrderDTO order) {
        amqpTemplate.convertAndSend(exchange, routingKey, order);
        System.out.println("Sent message to RabbitMQ: " + order);
    }
}

