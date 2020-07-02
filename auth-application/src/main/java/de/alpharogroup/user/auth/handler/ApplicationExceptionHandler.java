package de.alpharogroup.user.auth.handler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import de.alpharogroup.spring.exceptionhandling.ExceptionHandlerExtensions;
import de.alpharogroup.throwable.ThrowableExtensions;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.alpharogroup.user.auth.dto.ThrowableReport;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@ControllerAdvice(annotations = RestController.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler
{
	@Getter
	MessageSource messageSource;

	@ExceptionHandler
	public ResponseEntity<Object> handleException(Exception e, HttpServletRequest request)
	{
		return ExceptionHandlerExtensions.newResponseEntity(
			ExceptionHandlerExtensions
				.newExceptionViewModel(request,
					HttpStatus.BAD_REQUEST,
					ThrowableExtensions.newThrowableMessage(e, e.getMessage()),
					e.getLocalizedMessage()));
	}

}
