package com.springproj.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redistest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Disabled
    @Test
    void testsendmail(){
//       redisTemplate.opsForValue().set("email","suyashchamoli1@gmail.com"); //set kareage key va pair mai
        Object name = redisTemplate.opsForValue().get("name");//get karega
int a=1;


    }
}
