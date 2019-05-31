package de.alpharogroup.user.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.alpharogroup.random.RandomExtensions;
import de.alpharogroup.user.auth.jpa.entities.UserTokens;
import de.alpharogroup.user.auth.jpa.repositories.UserTokensRepository;
import de.alpharogroup.user.auth.service.api.UserTokensService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserTokensServiceImpl implements UserTokensService
{

	UserTokensRepository userTokensRepository;

	@Override
	public Optional<UserTokens> findByUsername(String username)
	{
		return userTokensRepository.findByUsername(username);
	}

	@Override
	public String getAutheticationToken(String username)
	{
		return userTokensRepository.getAutheticationToken(username);
	}

	@Override
	public boolean isValid(String token)
	{
		return userTokensRepository.existsByToken(token);
	}

	@Override
	public String newAuthenticationToken(String username)
	{

		Optional<UserTokens> optional = findByUsername(username);
		UserTokens userTokens;
		if (!optional.isPresent())
		{
			userTokens = userTokensRepository.save(newUserTokens(username));
		}
		else
		{
			userTokens = optional.get();
		}
		// check if expired
		final LocalDateTime now = LocalDateTime.now();
		if (userTokens.getExpiry().isBefore(now))
		{
			// expires in one year
			final LocalDateTime expiry = LocalDateTime.now().plusMonths(12);
			// create a token
			final String token = RandomExtensions.randomToken();
			userTokens.setExpiry(expiry);
			userTokens.setToken(token);
			userTokens = userTokensRepository.save(userTokens);
		}
		return userTokens.getToken();
	}

	/**
	 * New user tokens.
	 *
	 * @param username
	 *            the username
	 * @return the user tokens
	 */
	private UserTokens newUserTokens(String username)
	{
		return UserTokens.builder().expiry(LocalDateTime.now().plusMonths(12)).username(username)
			.token(RandomExtensions.randomToken()).build();
	}
}
