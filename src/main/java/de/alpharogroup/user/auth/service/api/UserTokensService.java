package de.alpharogroup.user.auth.service.api;

import java.util.Optional;

import de.alpharogroup.user.auth.jpa.entities.UserTokens;
import lombok.NonNull;

/**
 * The interface {@link UserTokensService}.
 */
public interface UserTokensService
{

	/**
	 * Find all token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the found {@link UserTokens} or null if no result.
	 */
	Optional<UserTokens> findByUsername(final @NonNull String username);

	/**
	 * Gets the authetication token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the authetication token or null if no result.
	 */
	String getAutheticationToken(final @NonNull String username);

	/**
	 * Checks if the given token is valid.
	 *
	 * @param token
	 *            the token to validate
	 * @return true, if the given token is valid otherwise false
	 */
	boolean isValid(final @NonNull String token);

	/**
	 * Factory method that creates a new authentication token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the new authentication token
	 */
	String newAuthenticationToken(final @NonNull String username);

}