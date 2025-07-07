package com.example.Order.Service.Feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("USER-SERVICE")
public interface OrderInterface {
}
