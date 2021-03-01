package de.alpharogroup.user.auth.principal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class GenericPrincipal<T> implements UserDetails
{
	private static final long serialVersionUID = 1L;

	@NonNull T user;
}
