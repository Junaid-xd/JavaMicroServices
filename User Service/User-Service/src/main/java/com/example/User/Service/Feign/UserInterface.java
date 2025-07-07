package com.example.User.Service.Feign;

import com.example.User.Service.DTO.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface UserInterface {
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO newOrder);

    @GetMapping("/getOrdersByUserId/{userid}")
    public ResponseEntity<?> getOrdersByUserID(@PathVariable Integer userid);


    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer orderId, @RequestBody OrderDTO orderDTO);

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId);
}

