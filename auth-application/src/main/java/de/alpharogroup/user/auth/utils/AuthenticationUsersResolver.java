package de.alpharogroup.user.auth.utils;

import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.principal.UsersPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthenticationUsersResolver
{

	public static Optional<Users> getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return Optional.empty();
		}
		UsersPrincipal principal = (UsersPrincipal) authentication.getPrincipal();
		return Optional.of(principal.getUser());
	}
}
