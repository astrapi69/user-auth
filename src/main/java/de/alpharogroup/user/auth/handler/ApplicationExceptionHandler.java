package de.alpharogroup.user.auth.handler;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

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
		return newResponseEntity(
			newThrowableReport(request, HttpStatus.BAD_REQUEST, e.getLocalizedMessage()));
	}

	ResponseEntity<Object> newResponseEntity(ThrowableReport throwableReport)
	{
		return new ResponseEntity<>(throwableReport, throwableReport.getStatus());
	}

	ThrowableReport newThrowableReport(HttpServletRequest httpServletRequest, HttpStatus status,
		String message)
	{
		return ThrowableReport.builder().status(status)
			.requestDescription(
				ServletUriComponentsBuilder.fromRequest(httpServletRequest).build().toUriString())
			.message(message).occurred(LocalDateTime.now()).build();
	}

}
