package com.poc.spring.redis;

import com.poc.spring.redis.producer.CustomerInfoPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataRedisPubSubApplication implements CommandLineRunner {

	@Autowired
	private CustomerInfoPublisher redisPublisher;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisPubSubApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		redisPublisher.publish();
		redisPublisher.publish();
		redisPublisher.publish();
		Thread.sleep(50);
		redisPublisher.publish();
		redisPublisher.publish();
	}
}
