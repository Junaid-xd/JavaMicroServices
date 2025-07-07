package com.example.Order.Service.controllers;

import com.example.Order.Service.DTO.OrderDTO;
import com.example.Order.Service.Models.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Order.Service.Services.orderService;

import java.util.List;

@RestController
public class ordersController {

    @Autowired
    private orderService orderservice;

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllOrders(){
        List<Orders> allOrders = orderservice.getAllOrders();
        if(!allOrders.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(allOrders);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Orders Yet");
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDto) {
        try {
            Orders newOrder = new Orders(orderDto);
            Orders createdOrder = orderservice.createOrder(newOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create order: " + e.getMessage());
        }
    }

    @GetMapping("/getOrdersByUserId/{userid}")
    public ResponseEntity<?> getOrdersByUserID(@PathVariable Integer userid){

        List<Orders> userOrders = orderservice.getOrdersByUserId(userid);

        if(!userOrders.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(userOrders);
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No orders found for this user");

    }


    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer orderId, @RequestBody OrderDTO orderDTO) {
        try {
            Orders updatedOrder = new Orders(orderDTO);
            Orders order = orderservice.updateOrder(orderId, updatedOrder);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }


    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        try {
            orderservice.deleteOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed: " + e.getMessage());
        }
    }

}
