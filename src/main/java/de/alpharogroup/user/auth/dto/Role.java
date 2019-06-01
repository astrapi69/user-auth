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
public class Role
{
	/** A description of the role. */
	private String description;
	/** The permissions of the role. */
	@Builder.Default
	private Set<Permission> permissions = new HashSet<>();
	/** The name of the role. */
	private String rolename;
}