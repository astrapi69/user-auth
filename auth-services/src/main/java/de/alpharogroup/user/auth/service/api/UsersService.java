package de.alpharogroup.user.auth.service.api;

import java.util.Optional;

import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import lombok.NonNull;


/**
 * The interface {@link UsersService}
 */
public interface UsersService
{

	/**
	 * Checks if a user exists with the given user name.
	 *
	 * @param username
	 *            the user name
	 * @return true, if successful
	 */
	boolean existsByUsername(final @NonNull String username);

	/**
	 * Checks if a user exists with the given email
	 *
	 * @param email
	 *            the email
	 * @return true, if successful
	 */
	boolean existsByEmail(final @NonNull String email);

	/**
	 * Find {@link Users} object from the given user name.
	 *
	 * @param username
	 *            the user name
	 * @return the found {@link Users} object
	 */
	Optional<Users> findByUsername(final @NonNull String username);

	/**
	 * Checks if the given {@link Users} object is in the given {@link Roles} object.
	 *
	 * @param user
	 *            the user
	 * @param role
	 *            the role
	 * @return true, if successful
	 */
	boolean isInRole(final @NonNull Users user, final @NonNull Roles role);

	/**
	 * Signs the given {@link Users} object out. Can be used to delete or clean up temporary user
	 * related data
	 *
	 * @param user
	 *            the user
	 *
	 * @return true, if the {@link Users} object successfully sign out
	 */
	boolean signOut(final @NonNull Users user);

}
