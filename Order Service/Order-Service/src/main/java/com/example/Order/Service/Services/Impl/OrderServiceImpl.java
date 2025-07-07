package com.example.Order.Service.Services.Impl;

import com.example.Order.Service.Models.Orders;
import com.example.Order.Service.Repository.OrdersRepository;
import com.example.Order.Service.Services.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements orderService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders createOrder(Orders newOrder) {
        return ordersRepository.save(newOrder);
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> getOrdersByUserId(Integer userId) {
        return ordersRepository.findByUserID(userId);
    }

    @Override
    public Orders updateOrder(Integer orderid, Orders updatedOrder) {
        Optional<Orders> existingOrderOptional = ordersRepository.findById(orderid);

        if (existingOrderOptional.isPresent()) {
            Orders existingOrder = existingOrderOptional.get();
            existingOrder.setItemName(updatedOrder.getItemName());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setUserID(updatedOrder.getUserID());

            return ordersRepository.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderid);
        }
    }

    @Override
    public void deleteOrder(Integer orderid) {
        Orders order = ordersRepository.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderid));

        ordersRepository.delete(order);
    }


}
