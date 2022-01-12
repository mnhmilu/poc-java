package com.poc.spring.redis.config;

import com.poc.spring.redis.consumer.CustomerInfoSubscriber;
import com.poc.spring.redis.producer.CustomerInfoPublisher;
import com.poc.spring.redis.producer.RedisCustomerInfoPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.concurrent.Executors;

@Configuration
@ComponentScan("com.poc.spring.redis")
public class RedisConfig {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {

		JedisConnectionFactory jedisConFactory
				= new JedisConnectionFactory();

		return jedisConFactory;

	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new CustomerInfoSubscriber());
	}

	@Bean
	RedisMessageListenerContainer redisContainer() {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory());
		container.addMessageListener(messageListener(), topic());
		container.setTaskExecutor(Executors.newFixedThreadPool(4));
		return container;
	}

	@Bean
	CustomerInfoPublisher redisPublisher() {
		return new RedisCustomerInfoPublisher(redisTemplate(), topic());
	}

	@Bean
	ChannelTopic topic() {
		return new ChannelTopic("pubsub:jsa-channel");
	}
}
