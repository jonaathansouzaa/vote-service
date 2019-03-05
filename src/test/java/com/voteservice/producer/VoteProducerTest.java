package com.voteservice.producer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class VoteProducerTest {

	@Mock
	private RabbitTemplate rabbitTemplate;
	
	@Mock
	private Queue queue;
	
	@InjectMocks
	private VoteProducer voteProducer;
	
	@Test
	public void testIfIsProducingTheMessage() {
		String message = "The session is closed.";
		
		String queueName = "queueName";
		when(queue.getName()).thenReturn(queueName);
		voteProducer.produceMessage(message);
		
		verify(queue).getName();
		verify(rabbitTemplate).convertAndSend(queueName, message);
	}
	
}
