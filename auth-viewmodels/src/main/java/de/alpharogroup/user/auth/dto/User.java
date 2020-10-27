package de.alpharogroup.user.auth.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User
{
	/** The attribute active, if true the user account is active. */
	boolean active;
	/** A Flag that indicates if the user account is locked or not. */
	boolean locked;
	/** The hash from the password hashed with sha512. */
	String password;
	/** The roles of the user. */
	@Builder.Default
	Set<Role> roles = new HashSet<>();
	/** The salt that is used to compute the hash. */
	String salt;
	/** The user name. */
	String username;
	/** The email */
	String email;
	/** The application this user is registered */
	Application application;
}
