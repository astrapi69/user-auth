package de.alpharogroup.user.auth.service.api;

import java.util.List;

import de.alpharogroup.user.auth.jpa.entities.Permissions;

/**
 * The interface {@link PermissionsService}
 */
public interface PermissionsService
{

	/**
	 * Factory method to create and save a new {@link Permissions} object.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @return the new {@link Permissions} object.
	 */
	Permissions save(String name, String description);

	/**
	 * Factory method to create and save a new {@link Permissions} object.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @param shortcut
	 *            the shortcut
	 * @return the new {@link Permissions} object.
	 */
	Permissions createAndSavePermissions(String name, String description, String shortcut);

	/**
	 * Find all {@link Permissions} objects by the given parameters.
	 *
	 * @param description
	 *            the description
	 * @param permissionName
	 *            the permission name
	 * @param shortcut
	 *            the shortcut
	 * @return the list of the found {@link Permissions} objects.
	 */
	List<Permissions> find(String description, String permissionName, String shortcut);

	/**
	 * Find the {@link Permissions} object by the given name.
	 *
	 * @param name
	 *            the name
	 * @return the found {@link Permissions} object or null if not.
	 */
	Permissions findByName(String name);

	/**
	 * Find the {@link Permissions} object by the given shortcut.
	 *
	 * @param shortcut
	 *            the shortcut
	 * @return the found {@link Permissions} object or null if not.
	 */
	Permissions findByShortcut(String shortcut);
}