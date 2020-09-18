package de.alpharogroup.user.auth.jpa.repositories;

import java.util.List;
import java.util.UUID;

import de.alpharogroup.user.auth.enums.ContactmethodType;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository public interface ContactmethodsRepository extends JpaRepository<Contactmethods, UUID>
{

	/**
	 * Find all the {@link Contactmethods} objects from the given contact value and the given
	 * {@link ContactmethodType}.
	 *
	 * @param contactmethod
	 *            the contact method
	 * @param contactvalue
	 *            the contact value
	 * @return the list of the found {@link Contactmethods} objects.
	 */
	@Query("select cm from Contactmethods cm where cm.contactmethod=:contactmethod and cm.contactvalue=:contactmethod")
	List<Contactmethods> find(@Param("contactmethod") ContactmethodType contactmethod, @Param("contactmethod")  String contactvalue);

	/**
	 * Find all the {@link Contactmethods} objects from the given user and the given
	 * {@link ContactmethodType}.
	 *
	 * @param contactmethod
	 *            the contact method
	 * @param user
	 *            the user
	 * @return the list of the found {@link Contactmethods} objects.
	 */
	@Query("select distinct cm from UserInfos u inner join u.contactmethods cm "
		+ "where u.owner=:user "
		+ "and cm.contactmethod=:contactmethod")
	List<Contactmethods> findContactmethods(@Param("contactmethod") ContactmethodType contactmethod, @Param("user") Users user);

}
