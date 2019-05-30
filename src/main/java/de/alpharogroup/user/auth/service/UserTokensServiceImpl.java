package de.alpharogroup.user.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public UserTokens find(String username)
	{
		return userTokensRepository.find(username);
	}

	@Override
	public List<UserTokens> findAll(String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutheticationToken(String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(String token)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String newAuthenticationToken(String username)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
