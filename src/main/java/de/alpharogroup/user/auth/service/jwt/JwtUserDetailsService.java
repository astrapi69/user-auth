package de.alpharogroup.user.auth.service.jwt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Optional;

import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.crypto.pw.PasswordEncryptor;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UsersRepository;
import de.alpharogroup.user.auth.principal.UsersPrincipal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUserDetailsService implements UserDetailsService {

	UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optionalUser = usersRepository.findByUsername(username);
		if(!optionalUser.isPresent()){
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Users user = optionalUser.get();
		return new UsersPrincipal(user);
	}

	public Users save(de.alpharogroup.user.auth.dto.User user) {
		Users newUser = new Users();
		newUser.setUsername(user.getUsername());
		// TODO set password with PasswordService ...
		newUser.setPw(bcryptEncoder.encode(user.getPw()));
		return usersRepository.save(newUser);
	}																																																																																																																		
}