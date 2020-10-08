package de.alpharogroup.user.auth.controller;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.auth.enums.ValidationErrors;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.dto.JwtResponse;
import de.alpharogroup.user.auth.dto.MessageBox;
import de.alpharogroup.user.auth.dto.Signup;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.JwtTokenService;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.api.RolesService;
import de.alpharogroup.user.auth.service.api.UsersService;
import de.alpharogroup.user.auth.service.jwt.JwtUserDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController @RequestMapping(ApplicationConfiguration.REST_VERSION + AuthenticationController.REST_PATH) @AllArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) public class AuthenticationController
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
	@CrossOrigin(origins = "*") @RequestMapping(value = SIGN_IN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) @ApiOperation(value = "authenticate with the given JwtRequest that contains the username and password") @ApiImplicitParams({
		@ApiImplicitParam(name = "jwtRequest", value = "The username", dataType = "JwtRequest", paramType = "body") }) public ResponseEntity<?> signIn(
		@Valid @RequestBody JwtRequest jwtRequest)
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
		String unauthorizedRedirectPath = "redirect:" + applicationProperties
			.getContextPath() + ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH + MessageController.UNAUTHORIZED_PATH;
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
			.body(unauthorizedRedirectPath);
	}

	@RequestMapping(value = SIGN_UP, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?> signUp(
		@Valid @RequestBody Signup signUpRequest)
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
