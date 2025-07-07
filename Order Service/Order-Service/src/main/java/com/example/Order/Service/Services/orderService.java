package com.example.Order.Service.Services;

import com.example.Order.Service.Models.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface orderService {
    Orders createOrder(Orders newOrder);

    List<Orders> getAllOrders();

    List<Orders> getOrdersByUserId(Integer userId);

    Orders updateOrder(Integer orderid, Orders updatedOrder);

    void deleteOrder(Integer orderid);
}
