package de.alpharogroup.user.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
import de.alpharogroup.user.auth.service.api.UsersService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
	public boolean existsByUsername(final @NonNull String username)
	{
		return usersRepository.existsByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Users> findByUsername(final @NonNull String username)
	{
		return usersRepository.findByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInRole(final @NonNull Users user, final @NonNull Roles role)
	{
		return user.getRoles().contains(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public boolean signOut(@NonNull Users user)
	{
		return true;
	}

}
