package de.alpharogroup.user.auth.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;

@Repository
public interface ResetPasswordsRepository extends JpaRepository<ResetPasswords, Long> {

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
