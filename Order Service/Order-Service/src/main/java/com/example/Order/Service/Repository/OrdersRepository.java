package com.example.Order.Service.Repository;

import com.example.Order.Service.Models.Orders;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserID(Integer userID);
}
