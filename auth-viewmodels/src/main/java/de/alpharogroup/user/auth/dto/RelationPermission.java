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
public class RelationPermission
{
	/** The permissions of the role. */
	@Builder.Default
	Set<Permission> permissions = new HashSet<>();
	/** The provider of the permissions. */
	User provider;
	/** The subscriber of the permissions. */
	User subscriber;
}
