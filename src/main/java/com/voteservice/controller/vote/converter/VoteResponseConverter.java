package com.voteservice.controller.vote.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.controller.topicvoting.response.TopicVotingResponse;
import com.voteservice.controller.vote.response.VoteResponse;
import com.voteservice.controller.vote.response.VoteResultResponse;
import com.voteservice.dto.VoteDTO;
import com.voteservice.exception.Messages;

@Component
public class VoteResponseConverter {

	public VoteResponse responseFromDto(VoteDTO voteDto) {
		if (Objects.isNull(voteDto)) {
			return new VoteResponse(Messages.YOU_CAN_NOT_VOTE); 
		}
		return new VoteResponse(Messages.YOUR_VOTE_IS_OK);
	}

	public VoteResponse responseFromDto(VoteDTO voteDto, String topicVotingDescription) {
		return new VoteResponse(new TopicVotingResponse(voteDto.getTopicVotingDescription()), 
				new VoteResultResponse(voteDto.getCountYes(), voteDto.getCountNo()),
				buildMessage(voteDto.getCountYes(), voteDto.getCountNo()));
	}

	private String buildMessage(Long countYes, Long countNo) {
		if (Objects.equals(countYes, countNo)) {
			return Messages.THE_OPTION_IS_THE_SAME;
		} else if (countYes > countNo) {
			return Messages.THE_OPTION_YES_WIN;
		}
		return Messages.THE_OPTION_NO_WIN;
	}
	
}
