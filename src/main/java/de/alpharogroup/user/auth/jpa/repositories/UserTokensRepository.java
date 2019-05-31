package de.alpharogroup.user.auth.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.user.auth.jpa.entities.UserTokens;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long>
{

	/**
	 * Checks if the given token is valid.
	 *
	 * @param token
	 *            the token to validate
	 * @return true, if the given token is valid otherwise false
	 */
	boolean existsByToken(String token);

	/**
	 * Find the {@link UserTokens} by token
	 *
	 * @param token
	 *            the token
	 * @return an optional with the entry if found
	 */
	Optional<UserTokens> findByToken(final String token);

	/**
	 * Find the {@link UserTokens} by user name
	 *
	 * @param username
	 *            the user name
	 * @return an optional with the entry if found
	 */
	Optional<UserTokens> findByUsername(final String username);

	/**
	 * Gets the authetication token from the given user name.
	 *
	 * @param username
	 *            the username
	 * @return the authetication token or null if no result.
	 */
	@Transactional
	@Query("select ut.token from UserTokens ut where ut.username = :username")
	String getAutheticationToken(@Param("username") final String username);

}
