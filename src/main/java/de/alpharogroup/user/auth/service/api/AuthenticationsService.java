package de.alpharogroup.user.auth.service.api;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.crypto.pw.PasswordEncryptor;
import de.alpharogroup.user.auth.jpa.entities.Users;

public interface AuthenticationsService extends Serializable
{

	/**
	 * Perform the authentication with the given email and password and return the result.
	 *
	 * @param emailOrUsername
	 *            the email or the user name of the {@link Users} object.
	 * @param password
	 *            the password
	 * @return the resulted {@link AuthenticationResult} object
	 */
	default public AuthenticationResult<Users, AuthenticationErrors> authenticate(
		final String emailOrUsername, final String password)
	{
		final AuthenticationResult<Users, AuthenticationErrors> authenticationResult = new AuthenticationResult<>();
		final UsersService userManagementBusinessService = getUsersService();
		// Check if username exists.
		final boolean usernameExists = userManagementBusinessService
			.existsUserWithUsername(emailOrUsername);
		if (usernameExists)
		{
			final Users user = userManagementBusinessService.findByUsername(emailOrUsername);
			return authorize(user, password, authenticationResult);
		}
		// set validation errors
		authenticationResult.getValidationErrors()
			.add(AuthenticationErrors.EMAIL_OR_USERNAME_DOES_NOT_EXIST);

		return authenticationResult;
	}


	/**
	 * Authorize given Users object.
	 *
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @param authenticationResult
	 *            the authentication result
	 * @return the authentication result
	 */
	default public AuthenticationResult<Users, AuthenticationErrors> authorize(final Users user,
		final String password,
		final AuthenticationResult<Users, AuthenticationErrors> authenticationResult)
	{
		if (user != null && user.isActive())
		{
			String hashedPassword = "";
			// Get hashed pw from db
			final String dbHashedPassword = user.getPw();
			// Get salt from db
			final String salt = user.getSalt();
			// get instance of PasswordEncryptor
			final PasswordEncryptor passwordService = PasswordEncryptor.getInstance();
			try
			{
				hashedPassword = passwordService.hashAndHexPassword(password, salt);
			}
			catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| InvalidKeyException | InvalidKeySpecException | UnsupportedEncodingException
				| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e)
			{
				authenticationResult.getValidationErrors()
					.add(AuthenticationErrors.PASSWORD_INVALID);
				return authenticationResult;
			}
			if (passwordService.match(hashedPassword, dbHashedPassword))
			{
				authenticationResult.setUser(user);
			}
			else
			{
				authenticationResult.getValidationErrors()
					.add(AuthenticationErrors.PASSWORD_INVALID);
			}
		}
		else
		{
			authenticationResult.getValidationErrors().add(AuthenticationErrors.UNREGISTERED);
		}
		return authenticationResult;
	}

	UsersService getUsersService();

	String newAuthenticationToken(String username);

}