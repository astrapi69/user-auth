package de.alpharogroup.user.auth.service.api;

import java.util.List;

import de.alpharogroup.user.auth.jpa.entities.UserTokens;

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
	UserTokens find(final String username);

	/**
	 * Find all token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the list
	 */
	List<UserTokens> findAll(final String username);

	/**
	 * Gets the authetication token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the authetication token or null if no result.
	 */
	String getAutheticationToken(final String username);

	/**
	 * Checks if the given token is valid.
	 *
	 * @param token
	 *            the token to validate
	 * @return true, if the given token is valid otherwise false
	 */
	boolean isValid(String token);

	/**
	 * Factory method that creates a new authentication token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the new authentication token
	 */
	String newAuthenticationToken(String username);

}