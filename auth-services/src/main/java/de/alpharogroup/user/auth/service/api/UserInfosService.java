package de.alpharogroup.user.auth.service.api;

import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import de.alpharogroup.user.auth.jpa.entities.Users;

/**
 * The interface {@link UserInfosService}
 */
public interface UserInfosService
{
	default String getFullName(UserInfos userData)
	{
		String firstname = userData.getFirstname();
		String lastname = userData.getLastname();
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
