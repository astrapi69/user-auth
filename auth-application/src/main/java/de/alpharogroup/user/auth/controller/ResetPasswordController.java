package de.alpharogroup.user.auth.controller;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.auth.enums.ValidationErrors;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.dto.JwtResponse;
import de.alpharogroup.user.auth.dto.MessageBox;
import de.alpharogroup.user.auth.dto.ResetPasswordMessage;
import de.alpharogroup.user.auth.dto.Signup;
import de.alpharogroup.user.auth.jpa.entities.Applications;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.JwtTokenService;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.api.ResetPasswordsService;
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

@RestController @RequestMapping(ApplicationConfiguration.REST_VERSION + ResetPasswordController.REST_PATH) @AllArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) 
public class ResetPasswordController
{

	public static final String REST_PATH = "/resetpassword";
	public static final String EMAIL_PATH = "/email";

	ResetPasswordsService resetPasswordsService;

	@RequestMapping(value = EMAIL_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> resetPasswordMessageForMail(String email) {
		ResetPasswordMessage resetPasswords = resetPasswordsService
			.generateResetPasswordMessageForMail(email);
		return ResponseEntity.ok(resetPasswords);
	}

}
