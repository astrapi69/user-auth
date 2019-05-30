package de.alpharogroup.user.auth.service.api;

import java.util.List;
import java.util.Set;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;

/**
 * The interface {@link RolesService}
 */
public interface RolesService
{

	/**
	 * Creates a new {@link Roles} object with the given arguments and save it. If it does exists it
	 * will return the existing.
	 *
	 * @param rolename
	 *            the role name.
	 * @param description
	 *            the description of the role.
	 * @return the created or existing {@link Roles} object.
	 */
	Roles createAndSaveRole(String rolename, String description);

	/**
	 * Creates a new {@link Roles} object with the given arguments and save it. If it does exists it
	 * will return the existing.
	 *
	 * @param rolename
	 *            the role name.
	 * @param description
	 *            the description of the role.
	 * @param permissions
	 *            the permissions to set for the role.
	 * @return the created or existing {@link Roles} object.
	 */
	Roles createAndSaveRole(String rolename, String description, Set<Permissions> permissions);

	/**
	 * Checks if a role exists with the given role name.
	 *
	 * @param rolename
	 *            the role name
	 * @return true, if successful
	 */
	boolean exists(final String rolename);

	/**
	 * Find all {@link Permissions} objects from the given {@link Roles} object.
	 *
	 * @param role
	 *            the given {@link Roles} object
	 * @return 's a list with all {@link Permissions} objects from the given {@link Roles} object.
	 */
	List<Permissions> findAllPermissions(Roles role);

	/**
	 * Find the {@link Roles} object with the given role name. If it does'nt exists it returns null.
	 *
	 * @param rolename
	 *            the role name
	 * @return the found {@link Roles} object or if it does'nt exists it returns null.
	 */
	Roles findRole(final String rolename);

	/**
	 * Find the {@link Roles} objects with the given role name.
	 *
	 * @param rolename
	 *            the rolename
	 * @return the found {@link Roles} objects
	 */
	List<Roles> findRoles(final String rolename);

}