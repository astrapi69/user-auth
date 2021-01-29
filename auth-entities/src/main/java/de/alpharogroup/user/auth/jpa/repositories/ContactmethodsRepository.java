/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
	@Query("select distinct cm from Contactmethods cm " +
		"where cm.contactmethod=:contactmethod " +
		"and cm.contactvalue=:contactvalue")
	List<Contactmethods> find(@Param("contactmethod") ContactmethodType contactmethod, @Param("contactvalue")  String contactvalue);

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
