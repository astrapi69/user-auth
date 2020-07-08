package de.alpharogroup.user.auth.handler;

import de.alpharogroup.spring.exceptionhandling.ExceptionHandlerExtensions;
import de.alpharogroup.throwable.ExceptionExtensions;
import de.alpharogroup.throwable.ThrowableExtensions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice(annotations = RestController.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler
{
	@Getter
	MessageSource messageSource;

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleException(Exception exception, HttpServletRequest request)
	{
		return ExceptionHandlerExtensions.newResponseEntity(
			ExceptionHandlerExtensions
				.newExceptionViewModel(request,
					HttpStatus.INTERNAL_SERVER_ERROR,
					ExceptionExtensions.getStackTrace(exception, exception.getMessage()),
					exception.getLocalizedMessage()));
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception,
		HttpServletRequest request)
	{
		return ExceptionHandlerExtensions.newResponseEntity(
			ExceptionHandlerExtensions.newExceptionViewModel(request,
				HttpStatus.BAD_REQUEST,
				ExceptionExtensions.getStackTrace(exception, "Invalid request"),
				"No proper arguments for the request"));
	}

	@ExceptionHandler({ NoSuchElementException.class })
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception,
		HttpServletRequest request)
	{
		return ExceptionHandlerExtensions.newResponseEntity(
			ExceptionHandlerExtensions.newExceptionViewModel(request, HttpStatus.NOT_FOUND,
				ExceptionExtensions.getStackTrace(exception, "No such element found"),
				"No such element found"));
	}

	@ExceptionHandler({ UnsupportedOperationException.class })
	public ResponseEntity<Object> handleUnsupportedOperationException(
		UnsupportedOperationException exception, HttpServletRequest request)
	{
		return ExceptionHandlerExtensions
			.newResponseEntity(ExceptionHandlerExtensions.newExceptionViewModel(request,
				HttpStatus.METHOD_NOT_ALLOWED,
				ExceptionExtensions.getStackTrace(exception,
					"Operation not supported request"),
				"Operation not supported"));
	}

}
