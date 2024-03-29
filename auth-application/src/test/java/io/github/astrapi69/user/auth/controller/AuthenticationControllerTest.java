/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.user.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import io.github.astrapi69.collection.list.ListFactory;
import io.github.astrapi69.collection.set.SetFactory;
import io.github.astrapi69.json.ObjectToJsonExtensions;
import io.github.astrapi69.throwable.RuntimeExceptionDecorator;
import io.github.astrapi69.user.auth.controller.AuthenticationController;
import io.github.astrapi69.user.auth.dto.JwtResponse;
import io.github.astrapi69.user.auth.dto.Signup;
import io.github.astrapi69.user.auth.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import io.github.astrapi69.spring.web.util.UrlExtensions;
import io.github.astrapi69.user.auth.configuration.ApplicationConfiguration;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(SpringExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest
{

	@LocalServerPort
	int randomServerPort;
	RestTemplate decoratedRestTemplate;
	@Autowired
	private TestRestTemplate restTemplate;

	public String getBaseUrl(int serverPort)
	{
		return UrlExtensions.getBaseUrl("http", "localhost", serverPort,
			ApplicationConfiguration.REST_VERSION, AuthenticationController.REST_PATH);
	}

	@BeforeEach
	public void prepare()
	{
		decoratedRestTemplate = this.restTemplate.getRestTemplate();
		List<HttpMessageConverter<?>> converters = decoratedRestTemplate.getMessageConverters();
		for (HttpMessageConverter converter : converters)
		{
			System.out.println(converter.toString());
		}
	}
	//
	// @Test
	// @Disabled
	// public void signin()
	// {
	// String restUrl;
	// HttpHeaders headers;
	// HttpEntity<String> requestEntity;
	// restUrl = UrlExtensions.generateUrl(getBaseUrl(randomServerPort),
	// AuthenticationController.SIGN_IN);
	// List<MediaType> acceptableMediaTypes = ListFactory.newArrayList();
	// acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
	// headers = new HttpHeaders();
	// headers.setAccept(acceptableMediaTypes);
	// headers.setContentType(MediaType.APPLICATION_JSON);
	// String json = "{\n" + " \"username\": \"foo\",\n" + " \"password\": \"bar\"\n" + "}";
	// requestEntity = new HttpEntity<>(json, headers);
	// ResponseEntity<JwtResponse> entity = this.restTemplate.postForEntity(restUrl, requestEntity,
	// JwtResponse.class);
	// assertNotNull(entity);
	// assertEquals(entity.getStatusCode(), HttpStatus.OK);
	// JwtResponse body = entity.getBody();
	// assertEquals(body.getUsername(), "foo");
	// assertEquals(body.getType(), "Bearer");
	// }
	//
	// @Test
	// @Disabled // TODO remove when implemented properly for junit5 ...
	// public void signup()
	// {
	// String restUrl;
	// HttpHeaders headers;
	// HttpEntity<String> requestEntity;
	// restUrl = UrlExtensions.generateUrl(getBaseUrl(randomServerPort),
	// AuthenticationController.SIGN_UP);
	// List<MediaType> acceptableMediaTypes = ListFactory.newArrayList();
	// acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
	// headers = new HttpHeaders();
	// headers.setAccept(acceptableMediaTypes);
	// headers.setContentType(MediaType.APPLICATION_JSON);
	// Signup signup = Signup.builder().username("xy").email("xy@z.org").password("z")
	// .roles(SetFactory.newHashSet(UserRole.testmember.name())).build();
	// String json = RuntimeExceptionDecorator
	// .decorate(() -> ObjectToJsonExtensions.toJson(signup));
	// requestEntity = new HttpEntity<>(json, headers);
	// ResponseEntity<String> entity = this.restTemplate.postForEntity(restUrl, requestEntity,
	// String.class);
	// assertNotNull(entity);
	// assertEquals(entity.getStatusCode(), HttpStatus.OK);
	// }

}
