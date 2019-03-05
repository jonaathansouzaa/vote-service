package com.voteservice.client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.voteservice.client.response.UserInfoResponse;
import com.voteservice.exception.Messages;
import com.voteservice.exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private RestClient restClient;
	
	private final String document = "01234567890";

	private String url = "https://user-info.herokuapp.com/users/" + document;
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(restClient, "url", url);
	}
	
	@Test
	public void shouldReturnAbleToVoteWhenIReceiveTheResponseAbleToVote() {
		URI uri = URI.create(url);
		when(restTemplate.getForObject(uri, UserInfoResponse.class)).thenReturn(new UserInfoResponse("ABLE_TO_VOTE"));
		
		assertTrue(restClient.validateDocument(document));
		verify(restTemplate).getForObject(uri, UserInfoResponse.class);
	}
	
	@Test
	public void shouldReturnUnableToVoteWhenIReceiveTheResponseUnableToVote() {
		URI uri = URI.create(url);
		when(restTemplate.getForObject(uri, UserInfoResponse.class)).thenReturn(new UserInfoResponse("UNABLE_TO_VOTE"));
		
		assertFalse(restClient.validateDocument(document));
		verify(restTemplate).getForObject(uri, UserInfoResponse.class);
	}
	
	@Test
	public void shouldReturnAnExceptionWhenIReceiveAnExceptionFromTheSupplier() {
		URI uri = URI.create(url);
		
		when(restTemplate.getForObject(uri, UserInfoResponse.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		assertThatThrownBy(() -> restClient.validateDocument(document))
			.isInstanceOf(NotFoundException.class)
			.hasMessage(Messages.THE_DOCUMENT_NOT_FOUND);
		
		verify(restTemplate).getForObject(uri, UserInfoResponse.class);
	}
	
}
