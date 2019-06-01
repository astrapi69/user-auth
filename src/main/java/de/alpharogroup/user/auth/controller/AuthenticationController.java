package de.alpharogroup.user.auth.controller;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.user.auth.dto.User;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationsService authenticationsService;

    AuthenticationResult<User, AuthenticationErrors> signIn(final @NonNull String emailOrUsername, final @NonNull String password) {
        AuthenticationResult<Users, AuthenticationErrors> authenticate = authenticationsService.authenticate(emailOrUsername, password);
        AuthenticationResult<User, AuthenticationErrors> result = AuthenticationResult.<User, AuthenticationErrors>builder()
                //.user()
                .validationErrors(authenticate.getValidationErrors())
                .build();
        return result;

    }
}
