package de.alpharogroup.user.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
import de.alpharogroup.user.auth.service.api.UsersService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements UsersService
{

	UsersRepository usersRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsUserWithUsername(final String username)
	{
		final Users users = findByUsername(username);
		if (users != null)
		{
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Roles> findRolesFromUser(final Users user)
	{
		return usersRepository.findRolesFromUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Users findByUsername(final String username)
	{
		return usersRepository.findByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInRole(final Users user, final Roles role)
	{
		final List<Roles> roles = findRolesFromUser(user);
		if (null != roles && !roles.isEmpty() && roles.contains(role))
		{
			return true;
		}
		return false;
	}

}
