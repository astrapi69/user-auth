package de.alpharogroup.user.auth.service.api;

import java.util.Optional;
import java.util.Set;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import lombok.NonNull;

/**
 * The interface {@link RolesService}
 */
public interface RolesService
{

	/**
	 * Checks if a role exists with the given role name.
	 *
	 * @param name
	 *            the role name
	 * @return true, if successful
	 */
	boolean existsByName(final @NonNull String name);

	/**
	 * Find the {@link Roles} object with the given role name. If it does'nt exists it returns null.
	 *
	 * @param name
	 *            the role name
	 * @return the found {@link Roles} object or if it does'nt exists it returns null.
	 */
	Optional<Roles> findByName(final @NonNull String name);

	/**
	 * Creates a new {@link Roles} object with the given arguments and save it. If it does exists it
	 * will return the existing.
	 *
	 * @param name
	 *            the role name.
	 * @param description
	 *            the description of the role.
	 * @return the created or existing {@link Roles} object.
	 */
	Roles save(final @NonNull String name, final @NonNull String description);

	/**
	 * Creates a new {@link Roles} object with the given arguments and save it. If it does exists it
	 * will return the existing.
	 *
	 * @param name
	 *            the role name.
	 * @param description
	 *            the description of the role.
	 * @param permissions
	 *            the permissions to set for the role.
	 * @return the created or existing {@link Roles} object.
	 */
	Roles save(final @NonNull String name, final @NonNull String description,
		final @NonNull Set<Permissions> permissions);

	/**
	 * Resolves the {@link Roles} objects from the given set of string role names
	 * @param stringRoles
	 *            the role names as string objects
	 * @return the found {@link Roles} objects
	 */
	Set<Roles> getRoles(final @NonNull Set<String> stringRoles);

}
