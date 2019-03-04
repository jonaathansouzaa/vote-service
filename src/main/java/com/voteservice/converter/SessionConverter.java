package com.voteservice.converter;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.model.Session;
import com.voteservice.model.TopicVoting;

@Component
public class SessionConverter {

	public Session converterTopicVotingToSession(TopicVoting topicVoting, LocalDateTime startingVoting, LocalDateTime finalVoting) {
		Session session = new Session();
		session.setTopicVoting(topicVoting);
		session.setStartingVoting(startingVoting);
		session.setFinalVoting(buildFinalVoting(finalVoting, startingVoting));
		return session;
	}

	private LocalDateTime buildFinalVoting(LocalDateTime finalVoting, LocalDateTime startingVoting) {
		if (Objects.isNull(finalVoting)) {
			return startingVoting.plusMinutes(1);
		}
		return finalVoting;
	}

}
