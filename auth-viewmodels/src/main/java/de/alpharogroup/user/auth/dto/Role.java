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
public class Role
{
	/** A description of the role. */
	String description;
	/** The permissions of the role. */
	@Builder.Default
	Set<Permission> permissions = new HashSet<>();
	/** The name of the role. */
	String name;
}
