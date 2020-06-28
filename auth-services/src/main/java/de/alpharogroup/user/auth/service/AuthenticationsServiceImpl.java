package de.alpharogroup.user.auth.service;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.api.UsersService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * The class {@link AuthenticationsServiceImpl} provides authentication methods.
 */
@Getter
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationsServiceImpl implements AuthenticationsService
{

	UsersService usersService;

}