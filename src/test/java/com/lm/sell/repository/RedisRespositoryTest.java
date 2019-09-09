package com.lm.sell.repository;

import com.lm.sell.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRespositoryTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void redisTest() {
        redisUtil.set("redisKey", "hello,world");
        String value = (String) redisUtil.get("redisKey");
        System.out.println("the value is " + value);
    }
}