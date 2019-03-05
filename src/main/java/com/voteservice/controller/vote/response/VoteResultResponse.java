package com.voteservice.controller.vote.response;

public class VoteResultResponse {

	private Long countYes;
	private Long countNo;

	public VoteResultResponse() {
	}
	
	public VoteResultResponse(Long countYes, Long countNo) {
		this.countYes = countYes;
		this.countNo = countNo;
	}

	public Long getCountYes() {
		return countYes;
	}

	public void setCountYes(Long countYes) {
		this.countYes = countYes;
	}

	public Long getCountNo() {
		return countNo;
	}

	public void setCountNo(Long countNo) {
		this.countNo = countNo;
	}

}
