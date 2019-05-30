package de.alpharogroup.user.auth.service;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.api.UserTokensService;
import de.alpharogroup.user.auth.service.api.UsersService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The class {@link AuthenticationsServiceImpl} provides authentication methods.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationsServiceImpl implements AuthenticationsService
{

	UsersService usersService;

	UserTokensService userTokensService;

	@Override
	public UsersService getUsersService()
	{
		return usersService;
	}

	@Override
	public String newAuthenticationToken(String username)
	{
		return userTokensService.newAuthenticationToken(username);
	}

}
