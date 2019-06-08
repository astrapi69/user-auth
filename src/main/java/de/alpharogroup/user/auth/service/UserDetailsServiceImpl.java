package de.alpharogroup.user.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
import de.alpharogroup.user.auth.principal.UsersPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService
{

	UsersRepository usersRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
	{
		Optional<Users> optionalUser = usersRepository.findByUsername(username);
		if (!optionalUser.isPresent())
		{
			throw new UsernameNotFoundException(username);
		}
		return new UsersPrincipal(optionalUser.get());
	}

}