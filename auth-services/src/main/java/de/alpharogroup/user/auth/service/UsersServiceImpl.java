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
package de.alpharogroup.user.auth.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import de.alpharogroup.auth.enums.ValidationErrors;
import de.alpharogroup.crypto.pw.PasswordEncryptor;
import de.alpharogroup.spring.service.api.GenericService;
import de.alpharogroup.user.auth.dto.Signup;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
import de.alpharogroup.user.auth.service.api.UsersService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Service
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersServiceImpl implements GenericService<Users, UUID, UsersRepository>, UsersService
{

	UsersRepository repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByUsername(final @NonNull String username)
	{
		return repository.existsByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByEmail(@NonNull String email)
	{
		return repository.existsByEmail(email);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Users> findByUsername(final @NonNull String username)
	{
		return repository.findByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Users> findByEmail(@NonNull String email)
	{
		return repository.findByEmail(email);
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
	@Override
	public boolean signOut(@NonNull Users user)
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


	public Users signUpUser(Signup model, Set<Roles> roles)
	{
		final String username = model.getUsername();
		final String email = model.getEmail();
		final String password = model.getPassword();

		final PasswordEncryptor passwordService = PasswordEncryptor.getInstance();

		final String salt = passwordService.getRandomSalt(8);
		String hashedPassword = "";
		try
		{
			hashedPassword = passwordService.hashAndHexPassword(password, salt);
		}
		catch (final Exception e)
		{
			throw new IllegalArgumentException(e);
		}
		Users newUser = Users.builder().active(true).locked(false).username(username).email(email)
			.salt(salt).password(hashedPassword).roles(roles).build();
		Users savedUser = repository.save(newUser);
		return savedUser;
	}
}
