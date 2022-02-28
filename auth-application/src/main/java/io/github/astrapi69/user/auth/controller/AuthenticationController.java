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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.astrapi69.auth.beans.AuthenticationResult;
import io.github.astrapi69.auth.enumtype.AuthenticationErrors;
import io.github.astrapi69.auth.enumtype.ValidationErrors;
import io.github.astrapi69.user.auth.configuration.ApplicationConfiguration;
import io.github.astrapi69.user.auth.configuration.ApplicationProperties;
import io.github.astrapi69.user.auth.dto.JwtRequest;
import io.github.astrapi69.user.auth.dto.JwtResponse;
import io.github.astrapi69.user.auth.dto.MessageBox;
import io.github.astrapi69.user.auth.dto.Signup;
import io.github.astrapi69.user.auth.jpa.entities.Roles;
import io.github.astrapi69.user.auth.jpa.entities.Users;
import io.github.astrapi69.user.auth.service.JwtTokenService;
import io.github.astrapi69.user.auth.service.api.AuthenticationsService;
import io.github.astrapi69.user.auth.service.api.RolesService;
import io.github.astrapi69.user.auth.service.api.UsersService;
import io.github.astrapi69.user.auth.service.jwt.JwtUserDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + AuthenticationController.REST_PATH)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController
{

	public static final String REST_PATH = "/auth";
	public static final String SIGN_IN = "/signin";
	public static final String SIGN_UP = "/signup";

	ApplicationProperties applicationProperties;
	AuthenticationsService authenticationsService;
	JwtTokenService jwtTokenService;
	JwtUserDetailsService userDetailsService;
	RolesService rolesService;
	UsersService usersService;
	PasswordEncoder encoder;

	/**
	 * Call this link <a href="https://localhost:8443/v1/auth/signin"> with post http-method </a>
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = SIGN_IN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "authenticate with the given JwtRequest that contains the username and password")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jwtRequest", value = "The username", dataType = "JwtRequest", paramType = "body") })
	public ResponseEntity<?> signIn(@Valid @RequestBody JwtRequest jwtRequest)
	{
		AuthenticationResult<Users, AuthenticationErrors> authenticate = authenticationsService
			.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		if (authenticate.isValid())
		{
			final UserDetails userDetails = userDetailsService
				.loadUserByUsername(jwtRequest.getUsername());
			final String token = jwtTokenService.newJwtToken(userDetails);
			Set<String> roles = authenticate.getUser().getRoles().stream()
				.map(roles1 -> roles1.getName()).collect(Collectors.toSet());
			JwtResponse jwtResponse = JwtResponse.builder().token(token).type("Bearer")
				.username(jwtRequest.getUsername()).roles(roles).build();
			return ResponseEntity.status(HttpStatus.OK.value()).body(jwtResponse);
		}
		String unauthorizedRedirectPath = "redirect:" + applicationProperties.getContextPath()
			+ ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH
			+ MessageController.UNAUTHORIZED_PATH;
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(unauthorizedRedirectPath);
	}

	@RequestMapping(value = SIGN_UP, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> signUp(@Valid @RequestBody Signup signUpRequest)
	{
		Optional<ValidationErrors> validationErrors = usersService.validate(signUpRequest);
		if (validationErrors.isPresent())
		{
			ValidationErrors error = validationErrors.get();
			if (ValidationErrors.EMAIL_EXISTS_ERROR.equals(error))
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
					.body("Email already exists");
			}
			if (ValidationErrors.USERNAME_EXISTS_ERROR.equals(error))
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
					.body("Username already exists");
			}
		}
		Set<Roles> roles;

		if (signUpRequest.getRoles() != null && !signUpRequest.getRoles().isEmpty())
		{
			roles = rolesService.getRoles(signUpRequest.getRoles());
		}
		else
		{
			Set<String> stringRoles = new HashSet<>();
			stringRoles.add("member");
			roles = rolesService.getRoles(stringRoles);
		}
		Users savedUser = usersService.signUpUser(signUpRequest, roles);

		return ResponseEntity.ok(MessageBox.builder()
			.message("User with id" + savedUser.getId() + " successfully created and signed up")
			.build());
	}

}
