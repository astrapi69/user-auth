package de.alpharogroup.user.auth.service.api;

import de.alpharogroup.user.auth.enums.ContactmethodType;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import de.alpharogroup.user.auth.jpa.entities.Users;

import java.util.List;

public interface ContactmethodsService
{

		/**
		 * Compare the given {@link Contactmethods} objects.
		 *
		 * @param contact
		 *            the contact
		 * @param compare
		 *            the compare
		 * @return true, if successful
		 */
		boolean compare(Contactmethods contact, Contactmethods compare);

		/**
		 * Check if a contact exist with given {@link Contactmethods}.
		 *
		 * @param contact
		 *            the contact
		 * @return true, if successful
		 */
		boolean existsContact(Contactmethods contact);

		/**
		 * Check if a contact exist with given contact value and the given {@link ContactmethodType}.
		 *
		 * @param contactValue
		 *            the contact value
		 * @param contactMethod
		 *            the contact method
		 * @return true, if successful
		 */
		boolean existsContact(String contactValue, ContactmethodType contactMethod);

		/**
		 * Find all the {@link Contactmethods} objects from the given contact value and the given
		 * {@link ContactmethodType}.
		 *
		 * @param contactValue
		 *            the contact value
		 * @param contactMethod
		 *            the contact method
		 * @return the list of the found {@link Contactmethods} objects.
		 */
		List<Contactmethods> findContact(String contactValue, ContactmethodType contactMethod);

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
		List<Contactmethods> findContactmethod(final ContactmethodType contactmethod, final Users user);
}
