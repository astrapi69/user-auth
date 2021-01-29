/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.user.auth.service.api;

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

public interface AuthenticationsService
{

	/**
	 * Perform the authentication with the given email and password and return the result.
	 * authentication (who are you?) with username<br>
	 *     and authorization with password and the roles that the user have
	 *
	 * @param emailOrUsername
	 *            the email or the user name of the {@link Users} object.
	 * @param password
	 *            the password
	 * @return the resulted {@link AuthenticationResult} object
	 */
	default AuthenticationResult<Users, AuthenticationErrors> authenticate(
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
	 * Authorize given {@link Users} object with the given password
	 *
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @return the authentication result
	 */
	default AuthenticationResult<Users, AuthenticationErrors> authorize(
		final @NonNull Users user, final @NonNull String password)
	{
		final AuthenticationResult<Users, AuthenticationErrors> authenticationResult = AuthenticationResult
			.<Users, AuthenticationErrors> builder().validationErrors(SetFactory.newHashSet())
			.build();
		if (user != null && user.isActive())
		{
			String hashedPassword = "";
			// Get hashed password from db
			final String dbHashedPassword = user.getPassword();
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

	/**
	 * Signs the given {@link Users} object out. Can be used to delete or clean up temporary user
	 * related data
	 *
	 * @param user
	 *            the user
	 *
	 * @return true, if the {@link Users} object successfully sign out
	 */
	default boolean signOut(final @NonNull Users user)
	{
		return getUsersService().signOut(user);
	}

	UsersService getUsersService();

}
