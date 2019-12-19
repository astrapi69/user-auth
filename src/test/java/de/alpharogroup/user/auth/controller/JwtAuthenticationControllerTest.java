package de.alpharogroup.user.auth.controller;

import de.alpharogroup.spring.web.util.UrlExtensions;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationControllerTest
{

	@Autowired private TestRestTemplate restTemplate;

	@LocalServerPort int randomServerPort;

	public String getBaseUrl(int serverPort)
	{
		return UrlExtensions.getBaseUrl("https", "localhost", serverPort,
			ApplicationConfiguration.REST_VERSION, JwtAuthenticationController.REST_PATH);
	}

	@Test public void getPrivateMessage()
	{
		String restUrl;
		HttpHeaders headers;
		restUrl = UrlExtensions.generateUrl(getBaseUrl(randomServerPort),
			"/private");
		headers = new HttpHeaders();
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> entity = this.restTemplate.exchange(restUrl, HttpMethod.GET,
			requestEntity, String.class);
		assertNotNull(entity);
	}

	@Test public void getPublicMessage()
	{
	}
}