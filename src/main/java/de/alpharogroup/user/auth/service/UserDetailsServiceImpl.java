package de.alpharogroup.user.auth.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
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
		Optional<Users> optional = usersRepository.findByUsername(username);
		if (!optional.isPresent())
		{
			throw new UsernameNotFoundException(username);
		}
		Users user = optional.get();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		user.getRoles().stream()
			.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(user.getUsername(), user.getPw(), grantedAuthorities);
	}

}