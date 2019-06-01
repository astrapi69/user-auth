package de.alpharogroup.user.auth.dto;

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
public class Permission
{
	/** A description for the permission. */
	private String description;
	/** The name from the permission. */
	private String permissionName;
	/** A shortcut for the permission. */
	private String shortcut;
}