package de.alpharogroup.user.auth.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import de.alpharogroup.user.auth.jpa.entities.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID>
{

	/**
	 * Checks if an {@link Users} object exists with the given user name
	 *
	 * @param username
	 *            the user name
	 * @return true if a {@link Users} object exists with the given user name
	 */
	boolean existsByUsername(final String username);

	/**
	 * Checks if an {@link Users} object exists with the given email
	 *
	 * @param email
	 *            the email
	 * @return true if a {@link Users} object exists with the given email
	 */
	boolean existsByEmail(final String email);

	/**
	 * Find {@link Users} object from the given user name.
	 *
	 * @param username
	 *            the user name
	 * @return the found {@link Users} object
	 */
	Optional<Users> findByUsername(final String username);

	/**
	 * Find {@link Users} object from the given users email
	 *
	 * @param email
	 *            the users email
	 * @return the found {@link Users} object
	 */
	Optional<Users> findByEmail(final String email);

}
