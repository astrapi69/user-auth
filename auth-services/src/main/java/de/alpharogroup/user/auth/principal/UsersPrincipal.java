package de.alpharogroup.user.auth.principal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.alpharogroup.user.auth.jpa.entities.Users;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersPrincipal implements UserDetails
{

	private static final long serialVersionUID = 1L;
	@NonNull
	Users user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		user.getRoles().stream().forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority("role:" + role.getName()));
			role.getPermissions().stream().forEach(permission -> grantedAuthorities
				.add(new SimpleGrantedAuthority("permission:" + permission.getName())));
		});
		return grantedAuthorities;
	}

	@Override
	public String getPassword()
	{
		return user.getPw();
	}

	@Override
	public String getUsername()
	{
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return user.isActive();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return isAccountNonExpired() && isAccountNonLocked();
	}

	@Override
	public boolean isEnabled()
	{
		return isAccountNonExpired();
	}

}
