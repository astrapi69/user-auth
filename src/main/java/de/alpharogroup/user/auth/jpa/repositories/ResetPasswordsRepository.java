package de.alpharogroup.user.auth.jpa.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;

@Repository
public interface ResetPasswordsRepository extends JpaRepository<ResetPasswords, Long>
{

	/**
	 * Finds the {@link ResetPasswords} object from the given {@link Users} object
	 *
	 * @param user
	 *            the user
	 * @return an optional with the entry if found
	 */
	@Transactional
	@Query("select rp from ResetPasswords rp where rp.user=:user")
	Optional<ResetPasswords> findByUser(@Param("user") Users user);

	/**
	 * Find the entry from the given {@link Users} and the given generated password(hashed)
	 *
	 * @param user
	 *            the user
	 * @param generatedPassword
	 *            the generated password(hashed) is the confirmationCode from the url query string
	 * @return an optional with the entry if found
	 */
	@Transactional
	@Query("select rp from ResetPasswords rp where rp.user=:user and rp.generatedPassword=:generatedPassword")
	Optional<ResetPasswords> findByUserAndGeneratedPassword(@Param("user") Users user,
		@Param("generatedPassword") String generatedPassword);

}
