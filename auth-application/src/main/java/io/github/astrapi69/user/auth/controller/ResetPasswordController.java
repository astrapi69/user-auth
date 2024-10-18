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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.astrapi69.user.auth.dto.NewPasswortRequest;
import io.github.astrapi69.user.auth.dto.ResetPasswordMessage;
import io.github.astrapi69.user.auth.dto.ResetPasswortRequest;
import io.github.astrapi69.user.auth.enums.ResetPasswordRest;
import io.github.astrapi69.user.auth.enums.Rest;
import io.github.astrapi69.user.auth.service.api.ResetPasswordsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(Rest.VERSION_1 + ResetPasswordRest.MAIN_PATH)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPasswordController
{
	ResetPasswordsService resetPasswordsService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = ResetPasswordRest.EMAIL_PATH, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPasswordMessageForMail(
		@Valid @RequestBody ResetPasswortRequest email, HttpServletRequest request)
	{
		String scheme = request.getScheme();
		String host = request.getHeader("Host");
		String contextPath = request.getContextPath();

		String resultPath = scheme + "://" + host + contextPath;

		ResetPasswordMessage resetPasswords = resetPasswordsService
			.generateResetPasswordMessageForMail(email.getEmail(), resultPath);
		return ResponseEntity.ok(resetPasswords);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = ResetPasswordRest.NEW_PASSWORD_PATH, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setNewPassword(NewPasswortRequest newPassword,
		HttpServletRequest request)
	{
		// TODO implement set new pw
		return ResponseEntity.ok(newPassword);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = ResetPasswordRest.VERIFY_TOKEN_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyToken(@RequestParam("token") String token,
		HttpServletRequest request)
	{
		// TODO implement verify token
		return ResponseEntity.ok(token);
	}

}
