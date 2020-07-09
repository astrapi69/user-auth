package de.alpharogroup.user.auth.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import de.alpharogroup.spring.exceptionhandling.ExceptionViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.ServletWebRequest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ControllerExceptionHandler.class })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControllerExceptionHandlerTest
{

	BindException bindException;
	@Mock
	BindingResult bindingResult;
	@Autowired
	ControllerExceptionHandler controllerExceptionHandler;
	Exception exception;

	@Mock
	FieldError fieldError;

	@Mock
	HttpServletRequest httpServletRequest;

	IllegalArgumentException illegalArgumentException;

	NoSuchElementException noSuchElementException;

	ServletWebRequest servletWebRequest;

	UnsupportedOperationException unsupportedOperationException;

	@Before
	public void prepare()
	{
		bindException = new BindException(bindingResult);
		noSuchElementException = new NoSuchElementException("Not found");
		unsupportedOperationException = new UnsupportedOperationException("Not supported");
		illegalArgumentException = new IllegalArgumentException("Illegal argument");
		exception = new Exception("General application error");
		when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
		when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("An error"));
		when(httpServletRequest.getHeaderNames()).thenReturn(Collections.emptyEnumeration());
		servletWebRequest = new ServletWebRequest(httpServletRequest);
	}

	@Test
	public void testHandleException()
	{
		String actual;
		String expected;
		when(fieldError.getDefaultMessage()).thenReturn("An error");
		ResponseEntity<Object> responseEntity = controllerExceptionHandler
			.handleException(exception, httpServletRequest);
		HttpStatus statusCode = responseEntity.getStatusCode();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
		Object body = responseEntity.getBody();
		assertTrue(body instanceof ExceptionViewModel);
		ExceptionViewModel exceptionViewModel = (ExceptionViewModel)body;
		actual = exceptionViewModel.getDeveloperMessage();
		expected = "General application error java.lang.Exception: General application error\n" ;
		assertTrue(actual.startsWith(expected));
	}

	@Test
	public void testHandleIllegalArgumentException()
	{
		String actual;
		String expected;
		when(fieldError.getDefaultMessage()).thenReturn("An error");
		ResponseEntity<Object> responseEntity = controllerExceptionHandler
			.handleIllegalArgumentException(illegalArgumentException, httpServletRequest);
		HttpStatus statusCode = responseEntity
			.getStatusCode();
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
		Object body = responseEntity.getBody();
		assertTrue(body instanceof ExceptionViewModel);
		ExceptionViewModel exceptionViewModel = (ExceptionViewModel)body;
		actual = exceptionViewModel.getDeveloperMessage();
		expected = "Invalid request java.lang.IllegalArgumentException: Illegal argument\n" ;
		assertTrue(actual.startsWith(expected));
	}

	@Test
	public void testHandleNoSuchElementException()
	{
		String actual;
		String expected;
		when(fieldError.getDefaultMessage()).thenReturn("An error");
		ResponseEntity<Object> responseEntity = controllerExceptionHandler
			.handleNoSuchElementException(noSuchElementException, httpServletRequest);
		HttpStatus statusCode = responseEntity
			.getStatusCode();
		assertEquals(HttpStatus.NOT_FOUND, statusCode);
		Object body = responseEntity.getBody();
		assertTrue(body instanceof ExceptionViewModel);
		ExceptionViewModel exceptionViewModel = (ExceptionViewModel)body;
		actual = exceptionViewModel.getDeveloperMessage();
		expected = "No such element found java.util.NoSuchElementException: Not found\n" ;
		assertTrue(actual.startsWith(expected));
	}

	@Test
	public void testHandleUnsupportedOperationException()
	{
		String actual;
		String expected;
		when(fieldError.getDefaultMessage()).thenReturn("An error");
		ResponseEntity<Object> responseEntity = controllerExceptionHandler
			.handleUnsupportedOperationException(unsupportedOperationException, httpServletRequest);
		HttpStatus statusCode = responseEntity
			.getStatusCode();
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, statusCode);
		Object body = responseEntity.getBody();
		assertTrue(body instanceof ExceptionViewModel);
		ExceptionViewModel exceptionViewModel = (ExceptionViewModel)body;
		actual = exceptionViewModel.getDeveloperMessage();
		expected = "Operation not supported request java.lang.UnsupportedOperationException: Not supported\n" ;
		assertTrue(actual.startsWith(expected));
	}

}