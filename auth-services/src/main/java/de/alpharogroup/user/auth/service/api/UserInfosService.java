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
	default String getFullName(Users user) {
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
