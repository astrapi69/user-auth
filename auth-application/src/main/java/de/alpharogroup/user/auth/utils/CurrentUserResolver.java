package de.alpharogroup.user.auth.utils;

import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.principal.UsersPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CurrentUserResolver
{

	public static <T> Optional<T> getAuthenticationPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return Optional.empty();
		}
		T principal = (T) authentication.getPrincipal();
		return Optional.of(principal);
	}

	public static Optional<Users> getCurrentUser() {
		Optional<UsersPrincipal> authenticationPrincipal = getAuthenticationPrincipal();
		if(authenticationPrincipal.isEmpty()){
			return Optional.empty();
		}
		UsersPrincipal usersPrincipal = authenticationPrincipal.get();
		return Optional.of(usersPrincipal.getUser());
	}
}
