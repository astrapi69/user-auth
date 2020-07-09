package de.alpharogroup.user.auth.controller;

import java.util.function.Function;

import de.alpharogroup.user.auth.configuration.ApplicationProperties;
import de.alpharogroup.user.auth.service.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.mapper.UserMapper;
import de.alpharogroup.user.auth.service.JwtTokenService;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.jwt.JwtUserDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + AuthenticationController.REST_PATH)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController
{

	public static final String REST_PATH = "/auth";
	public static final String AUTHENTICATE = "/authenticate";
	public static final String SIGNIN = "/signin";
	ApplicationProperties applicationProperties;
	AuthenticationsService authenticationsService;

	UserMapper userMapper;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	/**
	 * Call this link <a href="https://localhost:8443/v1/auth/authenticate"></a>
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = AUTHENTICATE, method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "authenticate with the given JwtRequest that contains the username and password")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authenticationRequest", value = "The username", dataType = "JwtRequest", paramType = "body") })
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenService.newJwtToken(userDetails);

		return ResponseEntity.ok(token);
	}

	/**
	 * Call this link <a href="https://localhost:8443/v1/auth/signin?username=foo&password=bar"></a>
	 * and adapt to your parameters.
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(path = SIGNIN, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "authenticate with the given username and password")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "The username", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "The plain password", dataType = "string", paramType = "query") })
	public ResponseEntity<String> signIn(
		@RequestParam(value = "username") @NonNull final String emailOrUsername,
		@RequestParam(value = "password") @NonNull final String password)
	{
		AuthenticationResult<Users, AuthenticationErrors> authenticate = authenticationsService
			.authenticate(emailOrUsername, password);
		String unauthorizedRedirectPath = "redirect:" +
				applicationProperties.getContextPath()+
				ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH +
				MessageController.UNAUTHORIZED_PATH;
		return ResponseEntity.status(authenticate.isValid()
			? HttpStatus.OK.value()
			: HttpStatus.UNAUTHORIZED.value()).body(authenticate.isValid()
			? authenticate.getUser().getId().toString()
				: unauthorizedRedirectPath);
	}

	protected Function<Users, User> getMapper() {
		return userMapper::toDto;
	}
}
