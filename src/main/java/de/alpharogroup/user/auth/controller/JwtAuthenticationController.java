package de.alpharogroup.user.auth.controller;

import javax.validation.Valid;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.auth.exceptions.UserAlreadyExistsException;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.configuration.JwtTokenExtensions;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.service.jwt.JwtUserDetailsService;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + JwtAuthenticationController.REST_PATH)
@CrossOrigin
public class JwtAuthenticationController {

	public static final String REST_PATH = "/jwt";
	public static final String AUTHENTICATE = "/authenticate";
	public static final String REGISTER = "/register";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenExtensions jwtTokenExtensions;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired private AuthenticationsService authenticationsService;

	/**
	 * Call this link <a href="https://localhost:8443/v1/jwt/authenticate"></a>
	 * and adapt to your parameters.
	 */
	@RequestMapping(value = AUTHENTICATE, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> newAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		AuthenticationResult<Users, AuthenticationErrors> authenticationResult = authenticationsService
			.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		if(authenticationResult.isValid()){
		final String token = jwtTokenExtensions.newJwtToken(userDetails);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.ok("invalid data");

	}

	@RequestMapping(value = REGISTER, method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) {
//		try
//		{
//			Users save = userDetailsService.save(user);
//			return ResponseEntity.ok(save);
//		}
//		catch (UserAlreadyExistsException e)
//		{
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
		return ResponseEntity.ok("This is a register message");
	}

	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public ResponseEntity<?> getPrivateMessage() {
		return ResponseEntity.ok("This is a private message");
	}

	@RequestMapping(value = "/ispublic", method = RequestMethod.GET)
	public ResponseEntity<?> getPublicMessage() {
		return ResponseEntity.ok("This is a public message");
	}

	protected void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}