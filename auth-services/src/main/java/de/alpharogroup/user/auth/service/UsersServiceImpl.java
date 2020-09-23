package de.alpharogroup.user.auth.service;

import java.util.Optional;

import de.alpharogroup.auth.enums.ValidationErrors;
import de.alpharogroup.user.auth.dto.Signup;
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
	public boolean existsByEmail(@NonNull String email)
	{
		return usersRepository.existsByEmail(email);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<ValidationErrors> validate(final @NonNull Signup model)
	{
		if (existsByEmail(model.getEmail()))
		{
			return Optional.of(ValidationErrors.EMAIL_EXISTS_ERROR);
		}
		if (existsByUsername(model.getUsername()))
		{
			return Optional.of(ValidationErrors.USERNAME_EXISTS_ERROR);
		}
		return Optional.empty();
	}
}
