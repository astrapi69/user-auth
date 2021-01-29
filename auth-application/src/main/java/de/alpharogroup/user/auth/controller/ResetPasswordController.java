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
