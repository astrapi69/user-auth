package de.alpharogroup.user.auth.controller;

import de.alpharogroup.user.auth.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.function.Function;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + "/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController
{

	AuthenticationsService authenticationsService;

	UserMapper userMapper;

	/**
	 * Call this link <a href="https://localhost:8443/v1/auth/signin?username=foo&password=bar"></a>
	 * and adapt to your parameters.
	 */
	@CrossOrigin(origins = "*")
	@GetMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "authenticate with the given username and password")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "The username", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "password", value = "The plain password", dataType = "string", paramType = "query") })
	public ResponseEntity<AuthenticationResult<User, AuthenticationErrors>> signIn(
		@RequestParam(value = "username") @NonNull final String emailOrUsername,
		@RequestParam(value = "password") @NonNull final String password)
	{
		AuthenticationResult<Users, AuthenticationErrors> authenticate = authenticationsService
			.authenticate(emailOrUsername, password);
		AuthenticationResult<User, AuthenticationErrors> result = AuthenticationResult
			.<User, AuthenticationErrors> builder()
			.user(getMapper().apply(authenticate.getUser()))
			.validationErrors(authenticate.getValidationErrors())
			.valid(authenticate.isValid())
			.build();
		return ResponseEntity.status(result.getValidationErrors().isEmpty()
			? HttpStatus.OK.value()
			: HttpStatus.UNAUTHORIZED.value()).body(result);
	}

	protected Function<Users, User> getMapper() {
		return userMapper::toDto;
	}
}
