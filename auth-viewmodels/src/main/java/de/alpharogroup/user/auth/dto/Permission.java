package de.alpharogroup.user.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission
{
	/** A description for the permission. */
	String description;
	/** The name from the permission. */
	String name;
	/** A shortcut for the permission. */
	String shortcut;
}