package com.voteservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoteConfig {

	@Value("${user.info.url}")
	private String url;
	
	@Value("${rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rabbitmq.routing-key}")
	private String routingKey;

	@Value("${rabbitmq.queue-name}")
	private String queueName;

	public String getUrl() {
		return url;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public String getQueueName() {
		return queueName;
	}
	
	@Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }
	
}
