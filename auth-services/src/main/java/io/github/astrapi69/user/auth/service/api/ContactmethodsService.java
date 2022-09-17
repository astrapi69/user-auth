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
package io.github.astrapi69.user.auth.service.api;

import java.util.List;

import io.github.astrapi69.user.auth.enums.ContactmethodType;
import io.github.astrapi69.user.auth.jpa.entities.Contactmethods;
import io.github.astrapi69.user.auth.jpa.entities.Users;

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
