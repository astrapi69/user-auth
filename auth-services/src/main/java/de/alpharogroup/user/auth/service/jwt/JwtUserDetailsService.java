package de.alpharogroup.user.auth.service.jwt;import java.util.Optional;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.core.userdetails.UserDetails;import org.springframework.security.core.userdetails.UserDetailsService;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import de.alpharogroup.auth.exceptions.UserAlreadyExistsException;import de.alpharogroup.crypto.pw.PasswordEncryptor;import de.alpharogroup.user.auth.dto.User;import de.alpharogroup.user.auth.jpa.entities.Users;import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;import de.alpharogroup.user.auth.mapper.UserMapper;import de.alpharogroup.user.auth.principal.UsersPrincipal;import lombok.AccessLevel;import lombok.AllArgsConstructor;import lombok.experimental.FieldDefaults;@Service@AllArgsConstructor@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)public class JwtUserDetailsService implements UserDetailsService {	UsersRepository usersRepository;	UserMapper userMapper;	@SuppressWarnings("unused")	@Autowired	private PasswordEncoder bcryptEncoder;	@Override	@Transactional(readOnly = true)	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		Optional<Users> optionalUser = usersRepository.findByUsername(username);		if(!optionalUser.isPresent()){			throw new UsernameNotFoundException("User not found with username: " + username);		}		Users user = optionalUser.get();		return new UsersPrincipal(user);	}	public Users save(User user) throws UserAlreadyExistsException	{		if(usersRepository.existsByUsername(user.getUsername())){			throw new UserAlreadyExistsException("user already exists");		}		Users newUser = userMapper.toEntity(user);		final PasswordEncryptor passwordService = PasswordEncryptor.getInstance();		String salt = passwordService.getRandomSalt(8);		newUser.setSalt(salt);		String hashedPassword;		try		{			hashedPassword = passwordService.hashAndHexPassword(user.getPw(), salt);			newUser.setPw(hashedPassword);		}		catch (final Exception e)		{			throw new IllegalArgumentException(e);		}		return usersRepository.save(newUser);	}}