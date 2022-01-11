package com.poc.spring.redis;


import com.poc.spring.redis.producer.CustomerInfoPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringDataRedisPubSubApplicationTests {
    @Autowired
    private CustomerInfoPublisher redisPublisher;

    @Test
    public void testRedisConfig() throws InterruptedException {
        redisPublisher.publish();
        redisPublisher.publish();
        redisPublisher.publish();
        Thread.sleep(50);
        redisPublisher.publish();
        redisPublisher.publish();
    }

}
