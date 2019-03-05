package com.voteservice.controller.vote.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.voteservice.controller.vote.response.VoteResponse;
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

}
