package de.alpharogroup.user.auth.controller;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.alpharogroup.collections.array.ArrayFactory;
import de.alpharogroup.collections.list.ListFactory;
import de.alpharogroup.spring.generics.ParameterizedTypeReferenceFactory;
import de.alpharogroup.spring.web.util.UrlExtensions;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest
{

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;
	RestTemplate decoratedRestTemplate;

	public String getBaseUrl(int serverPort)
	{
		return UrlExtensions.getBaseUrl("http", "localhost", serverPort,
			ApplicationConfiguration.REST_VERSION, AuthenticationController.REST_PATH);
	}

	@Before
	public void prepare()
	{
		decoratedRestTemplate = this.restTemplate.getRestTemplate();
		List<HttpMessageConverter<?>> converters = decoratedRestTemplate.getMessageConverters();
		for (HttpMessageConverter converter : converters)
		{
			System.out.println(converter.toString());
		}
	}

	@Test public void createAuthenticationToken()
	{
		String restUrl;
		HttpHeaders headers;
		HttpEntity<String> requestEntity;
		restUrl = UrlExtensions.generateUrl(getBaseUrl(randomServerPort), AuthenticationController.AUTHENTICATE);
		List<MediaType> acceptableMediaTypes = ListFactory.newArrayList();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		String json = "{\n" + "  \"username\": \"foo\",\n" + "  \"password\": \"bar\"\n" + "}";
		requestEntity = new HttpEntity<>(json, headers);
		ResponseEntity<String> entity = this.restTemplate.postForEntity(restUrl,
			requestEntity,
			String.class);
		assertNotNull(entity);
		assertEquals(entity.getStatusCode(), HttpStatus.OK);
	}

//	@Ignore
	@Test public void signIn()
	{
		Map<String, String> urlParams;
		String restUrl;
		HttpHeaders headers;
		ResponseEntity<String> entity;
			restUrl = UrlExtensions.generateUrl(getBaseUrl(randomServerPort), AuthenticationController.SIGNIN);

		urlParams = new HashMap<>();
		urlParams.put("username", "foo");
		urlParams.put("password", "bar");
		entity = this.restTemplate.getForEntity(restUrl, String.class, urlParams);
		assertNotNull(entity);
		assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

}