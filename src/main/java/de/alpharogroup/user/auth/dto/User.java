package de.alpharogroup.user.auth.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User
{
	/** The attribute active, if true the user account is active. */
	private boolean active;
	/** A Flag that indicates if the user account is locked or not. */
	private boolean locked;
	/** The hash from the password hashed with sha512. */
	private String pw;
	/** The roles of the user. */
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
	/** The salt that is used to compute the hash. */
	private String salt;
	/** The user name. */
	private String username;
}