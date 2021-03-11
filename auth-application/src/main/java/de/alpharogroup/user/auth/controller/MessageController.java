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

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH)
@CrossOrigin
public class MessageController
{

	public static final String REST_PATH = "/jwt";
	public static final String PRIVATE_PATH = "/private";
	public static final String MEMBER_PATH = "/member";
	public static final String ISPUBLIC_PATH = "/ispublic";
	public static final String UNAUTHORIZED_PATH = "/unauthorized";

	/**
	 * Call this link <a href="https://localhost:8443/v1/jwt/private"></a>
	 */
	@RequestMapping(value = PRIVATE_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getPrivateMessage()
	{
		return ResponseEntity.ok("This is a private message");
	}

	@RequestMapping(value = ISPUBLIC_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getPublicMessage()
	{
		return ResponseEntity.ok("This is a public message");
	}

	@RequestMapping(value = UNAUTHORIZED_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getUnauthorized()
	{
		return ResponseEntity.ok("Sign in failed");
	}


	@RequestMapping(value = MEMBER_PATH, method = RequestMethod.GET)
	@PreAuthorize("hasRole('MEMBER')")
	public String member()
	{
		return "Member area";
	}

}
