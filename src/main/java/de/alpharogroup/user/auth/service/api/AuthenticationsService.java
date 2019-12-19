package de.alpharogroup.user.auth.service.api;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.collections.set.SetFactory;
import de.alpharogroup.crypto.pw.PasswordEncryptor;
import de.alpharogroup.user.auth.jpa.entities.Users;
import lombok.NonNull;

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
		final @NonNull String emailOrUsername, final @NonNull String password)
	{

		Optional<Users> byUsername = getUsersService().findByUsername(emailOrUsername);
		if (byUsername.isPresent())
		{
			return authorize(byUsername.get(), password);
		}
		// set validation errors
		return AuthenticationResult.<Users, AuthenticationErrors> builder()
			.validationErrors(
				SetFactory.newHashSet(AuthenticationErrors.EMAIL_OR_USERNAME_DOES_NOT_EXIST))
			.build();
	}

	/**
	 * Authorize given Users object.
	 *
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @return the authentication result
	 */
	default public AuthenticationResult<Users, AuthenticationErrors> authorize(
		final @NonNull Users user, final @NonNull String password)
	{
		final AuthenticationResult<Users, AuthenticationErrors> authenticationResult = AuthenticationResult
			.<Users, AuthenticationErrors> builder().validationErrors(SetFactory.newHashSet())
			.build();
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
				authenticationResult.setValid(true);
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