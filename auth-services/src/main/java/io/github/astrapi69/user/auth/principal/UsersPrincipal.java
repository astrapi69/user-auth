/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.user.auth.principal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.github.astrapi69.user.auth.jpa.entities.Users;

public class UsersPrincipal extends GenericPrincipal<Users>
{
	private static final long serialVersionUID = 1L;

	public UsersPrincipal(Users users)
	{
		super(users);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		getUser().getRoles().stream().forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority("role:" + role.getName()));
			role.getPermissions().stream().forEach(permission -> grantedAuthorities
				.add(new SimpleGrantedAuthority("permission:" + permission.getName())));
		});
		return grantedAuthorities;
	}

	@Override
	public String getPassword()
	{
		return getUser().getPassword();
	}

	@Override
	public String getUsername()
	{
		return getUser().getUsername();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return getUser().isActive();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return !getUser().isLocked();
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
