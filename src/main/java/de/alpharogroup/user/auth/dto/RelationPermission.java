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
public class RelationPermission
{
	/** The subscriber of the permissions. */
	private User subscriber;
	/** The provider of the permissions. */
	private User provider;
	/** The permissions of the role. */
	@Builder.Default
	private Set<Permission> permissions = new HashSet<>();
}
