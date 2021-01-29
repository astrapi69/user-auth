package de.alpharogroup.user.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.dto.ResetPasswordMessage;
import de.alpharogroup.user.auth.service.api.ResetPasswordsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + ResetPasswordController.REST_PATH)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPasswordController
{

	public static final String REST_PATH = "/resetpassword";
	public static final String EMAIL_PATH = "/email";

	ResetPasswordsService resetPasswordsService;

	@RequestMapping(value = EMAIL_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> resetPasswordMessageForMail(String email, HttpServletRequest request)
	{
		String scheme = request.getScheme();
		String host = request.getHeader("Host");
		String contextPath = request.getContextPath();

		String resultPath = scheme + "://" + host + contextPath;

		ResetPasswordMessage resetPasswords = resetPasswordsService
			.generateResetPasswordMessageForMail(email, resultPath);
		return ResponseEntity.ok(resetPasswords);
	}

}
