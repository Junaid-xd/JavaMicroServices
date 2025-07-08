package com.example.User.Service.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class RedisUtil {


    @Autowired
    private StringRedisTemplate redisTemplate;

    public void saveToken(String userId, String jwtToken) {
        redisTemplate.opsForValue().set(userId, jwtToken, Duration.ofHours(1)); // 1 hour expiry
    }

    public String getToken(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    public void deleteToken(String userId) {
        redisTemplate.delete(userId);
    }
}
