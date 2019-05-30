package de.alpharogroup.user.auth.service.api;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;

/**
 * The interface {@link ResetPasswordsService}.
 */
public interface ResetPasswordsService
{

	/**
	 * Finds the {@link ResetPasswords} object from the given {@link Users} object.
	 *
	 * @param user
	 *            the user
	 * @return the entry of the found {@link ResetPasswords} or null if not found
	 */
	ResetPasswords findResetPassword(Users user);


	/**
	 * Find the entry from the given {@link Users} and the given generated password(hashed).
	 *
	 * @param user
	 *            the user
	 * @param generatedPassword
	 *            the generated password(hashed) is the confirmationCode from the url query string
	 * @return the entry of the found {@link ResetPasswords} or null if not found
	 */
	ResetPasswords findResetPassword(Users user, String generatedPassword);

}