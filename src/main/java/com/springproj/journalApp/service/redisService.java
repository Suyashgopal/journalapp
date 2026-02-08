package com.springproj.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class redisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {
            String json = redisTemplate.opsForValue().get(key);

            if (json == null) {
                return null;
            }

            return mapper.readValue(json, entityClass);

        } catch (Exception e) {
            log.error("Exception while getting from Redis", e);
            return null;
        }
    }

    public void set(String key, Object value, Long ttl) {
        try {
            String json = mapper.writeValueAsString(value);

            redisTemplate.opsForValue()
                    .set(key, json, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Exception while setting to Redis", e);
        }
    }
}


