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

import java.util.Optional;

import io.github.astrapi69.user.auth.dto.ResetPasswordMessage;
import io.github.astrapi69.user.auth.jpa.entities.ResetPasswords;
import io.github.astrapi69.user.auth.jpa.entities.Users;
import lombok.NonNull;

/**
 * The interface {@link ResetPasswordsService}.
 */
public interface ResetPasswordsService
{

	/**
	 * Finds the {@link ResetPasswords} object from the given {@link Users} object.
	 *
	 * @param user
	 *            the user
	 * @return an optional with the entry if found
	 */
	Optional<ResetPasswords> findByUser(final @NonNull Users user);


	/**
	 * Find the entry from the given {@link Users} and the given generated password(hashed).
	 *
	 * @param user
	 *            the user
	 * @param generatedPassword
	 *            the generated password(hashed) is the confirmationCode from the url query string
	 * @return an optional with the entry if found
	 */
	Optional<ResetPasswords> findByUserAndGeneratedPassword(final @NonNull Users user,
		final @NonNull String generatedPassword);

	/**
	 * Generate new entry from the given email that can be used for send email with email service
	 *
	 * @param email
	 *            the user email
	 * @param contextPath
	 *            the context path
	 * @return an optional with the entry if generated otherwise empty
	 */
	ResetPasswordMessage generateResetPasswordMessageForMail(final @NonNull String email,
		final @NonNull String contextPath);

}
