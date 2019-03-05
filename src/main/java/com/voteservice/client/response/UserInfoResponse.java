package com.voteservice.client.response;

public class UserInfoResponse {

	private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
	
	private String status;

	public UserInfoResponse() {
	}
	
	public UserInfoResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean isAbleToVote() {
		return getStatus().equalsIgnoreCase(ABLE_TO_VOTE);
	}

}
