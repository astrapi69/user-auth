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
public class Application
{
	/** The domain name of this application. */
	String domainName;
	/** The info email of this application. */
	String email;
	/** The name. */
	String name;
	/** The allowed permissions for this application */
	@Builder.Default
	Set<Permission> permissions = new HashSet<>();
	/** The allowed roles for this application */
	@Builder.Default
	Set<Role> roles = new HashSet<>();
}
