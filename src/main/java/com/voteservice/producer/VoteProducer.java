package com.voteservice.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteProducer {

	private RabbitTemplate rabbitTemplate;
	private Queue queue;

	@Autowired
	public VoteProducer(RabbitTemplate rabbitTemplate, Queue queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
	}
	
	public void produceMessage(String message) {
		rabbitTemplate.convertAndSend(queue.getName(), message);
	}

}
