package de.alpharogroup.user.auth.service.api;

import java.util.Optional;
import java.util.Set;

import de.alpharogroup.auth.enums.ValidationErrors;
import de.alpharogroup.user.auth.dto.Signup;
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
	 * Find {@link Users} object from the given users email
	 *
	 * @param email
	 *            the users email
	 * @return the found {@link Users} object
	 */
	Optional<Users> findByEmail(final @NonNull String email);

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

	/**
	 * Validate the given {@link Signup} object.
	 *
	 * @param model
	 *            the {@link Signup} object.
	 * @return If successful an empty optional of {@link ValidationErrors} object will be return
	 * otherwise the optional with the validation error will be return
	 */
	Optional<ValidationErrors> validate(@NonNull Signup model);

	/**
	 * Sign up process for insert a new user in the database
	 *
	 * @param model
	 *            the model
	 * @param roles
	 *            the roles
	 * @return the {@link Users} object
	 */
	Users signUpUser(Signup model, Set<Roles> roles);

}
