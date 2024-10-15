package io.github.astrapi69.user.auth.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
	 * Endpoint for user sign-in.
	 *
	 * @param jwtRequest
	 *            Contains the username and password for authentication
	 * @return Response entity with the JWT token or an error message
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = SIGN_IN, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Authenticate with the given JwtRequest that contains the username and password", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = JwtRequest.class))), responses = {
			@ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	public ResponseEntity<?> signIn(@Valid @RequestBody JwtRequest jwtRequest)
	{
		AuthenticationResult<Users, AuthenticationErrors> authenticate = authenticationsService
			.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		if (authenticate.isValid())
		{
			final UserDetails userDetails = userDetailsService
				.loadUserByUsername(jwtRequest.getUsername());
			final String token = jwtTokenService.newJwtToken(userDetails);
			Set<String> roles = authenticate.getUser().getRoles().stream().map(Roles::getName)
				.collect(Collectors.toSet());
			JwtResponse jwtResponse = JwtResponse.builder().token(token).type("Bearer")
				.username(jwtRequest.getUsername()).roles(roles).build();
			return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
		}
		String unauthorizedRedirectPath = "redirect:" + applicationProperties.getContextPath()
			+ ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH
			+ MessageController.UNAUTHORIZED_PATH;
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedRedirectPath);
	}

	/**
	 * Endpoint for user sign-up.
	 *
	 * @param signUpRequest
	 *            Contains user registration data
	 * @return Response entity with success message or error message
	 */
	@RequestMapping(value = SIGN_UP, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Sign up a new user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Signup.class))), responses = {
			@ApiResponse(responseCode = "200", description = "User created", content = @Content(schema = @Schema(implementation = MessageBox.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content) })
	public ResponseEntity<?> signUp(@Valid @RequestBody Signup signUpRequest)
	{
		Optional<ValidationErrors> validationErrors = usersService.validate(signUpRequest);
		if (validationErrors.isPresent())
		{
			ValidationErrors error = validationErrors.get();
			if (ValidationErrors.EMAIL_EXISTS_ERROR.equals(error))
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
			}
			if (ValidationErrors.USERNAME_EXISTS_ERROR.equals(error))
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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
			.message("User with id " + savedUser.getId() + " successfully created and signed up")
			.build());
	}
}
