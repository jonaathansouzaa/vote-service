package com.voteservice.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voteservice.converter.SessionConverter;
import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;
import com.voteservice.repository.SessionRepository;

@Service
public class SessionService {

	private SessionRepository sessionRepository;
	private SessionConverter sessionConverter;

	@Autowired
	public SessionService(SessionRepository sessionRepository, SessionConverter sessionConverter) {
		this.sessionRepository = sessionRepository;
		this.sessionConverter = sessionConverter;
	}
	
	public Boolean openSession(TopicVoting topicVoting, LocalDateTime finalVoting) {
		Session session = sessionConverter.converterTopicVotingToSession(topicVoting, LocalDateTime.now(), finalVoting);
		Session sessionSaved = sessionRepository.save(session);
		return Objects.nonNull(sessionSaved);
	}

}
