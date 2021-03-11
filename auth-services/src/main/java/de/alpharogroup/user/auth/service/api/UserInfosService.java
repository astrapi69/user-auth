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
package de.alpharogroup.user.auth.service.api;

import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import de.alpharogroup.user.auth.jpa.entities.Users;

/**
 * The interface {@link UserInfosService}
 */
public interface UserInfosService
{

	/**
	 * Find the {@link UserInfos} object by the given {@link Users} object.
	 *
	 * @param user
	 *            the {@link Users} object
	 * @return the found {@link UserInfos} object or null if does not exist.
	 */
	UserInfos findByOwner(Users user);

	/**
	 * Gets the full name from the given {@link Users} object.
	 *
	 * @param user
	 *            the user
	 * @return the full name
	 */
	default String getFullName(Users user)
	{
		String fullName = getFullName(findByOwner(user));
		return fullName.isEmpty() ? user.getUsername() : fullName;
	}

	/**
	 * Gets the full name from the given {@link UserInfos} object.
	 *
	 * @param userInfos
	 *            the user info
	 * @return the full name
	 */
	default String getFullName(UserInfos userInfos)
	{
		String firstname = userInfos.getFirstname();
		String lastname = userInfos.getLastname();
		StringBuilder sb = new StringBuilder();
		if (firstname != null && !firstname.isEmpty())
		{
			sb.append(firstname);
		}
		if (lastname != null && !lastname.isEmpty())
		{
			if (firstname != null && !firstname.isEmpty())
			{
				sb.append(" ");
			}
			sb.append(lastname);
		}
		return sb.toString().trim();
	}

}
