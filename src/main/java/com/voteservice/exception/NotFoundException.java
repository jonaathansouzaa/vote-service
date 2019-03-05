package com.voteservice.exception;

import org.springframework.web.client.RestClientException;

public class NotFoundException extends RestClientException {

	public NotFoundException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -1982323927440862743L;

}
