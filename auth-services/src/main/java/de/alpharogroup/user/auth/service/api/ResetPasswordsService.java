package de.alpharogroup.user.auth.service.api;

import java.util.Optional;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;
import lombok.NonNull;

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
	 * @return an optional with the entry if found
	 */
	Optional<ResetPasswords> findByUser(final @NonNull Users user);


	/**
	 * Find the entry from the given {@link Users} and the given generated password(hashed).
	 *
	 * @param user
	 *            the user
	 * @param generatedPassword
	 *            the generated password(hashed) is the confirmationCode from the url query string
	 * @return an optional with the entry if found
	 */
	Optional<ResetPasswords> findByUserAndGeneratedPassword(final @NonNull Users user,
		final @NonNull String generatedPassword);

	/**
	 * Generate new entry from the given email that can be used for send email with email service
	 *
	 * @param email
	 *            the user email
	 * @return an optional with the entry if generated otherwise empty
	 */
	Optional<ResetPasswords> generateResetPasswordMessageForMail(String email);

}
