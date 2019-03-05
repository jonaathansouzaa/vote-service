package com.voteservice.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.voteservice.producer.VoteProducer;
import com.voteservice.service.SessionService;

@Component
public class VoteServiceScheduling {

	private SessionService sessionService;
	private VoteProducer voteProducer;

	@Autowired
	public VoteServiceScheduling(SessionService sessionService, VoteProducer voteProducer) {
		this.sessionService = sessionService;
		this.voteProducer = voteProducer;
	}
	
	@Scheduled(cron = "15 * * * * *")
	public void verifyWhoTheTopicVotingSessionIsClosed() {
		System.out.println("Scheduling run...");
		List<String> listOfSessionClosed = sessionService.doHaveAnOpenSessionThatCanBeClosed();
		listOfSessionClosed.forEach(session -> {
			System.out.println("Producing message: " + session);
			voteProducer.produceMessage(session);
		});
		System.out.println("Scheduling finished...");
	}
	
}
