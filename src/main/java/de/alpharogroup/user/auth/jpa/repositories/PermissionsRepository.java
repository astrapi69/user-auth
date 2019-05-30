package de.alpharogroup.user.auth.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Integer> {

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
