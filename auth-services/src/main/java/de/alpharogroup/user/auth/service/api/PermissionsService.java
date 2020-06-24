package de.alpharogroup.user.auth.service.api;

import java.util.Optional;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import lombok.NonNull;

/**
 * The interface {@link PermissionsService}
 */
public interface PermissionsService
{

	/**
	 * Find the {@link Permissions} object by the given name.
	 *
	 * @param name
	 *            the name
	 * @return the found {@link Permissions} object or null if not.
	 */
	Optional<Permissions> findByName(final @NonNull String name);

	/**
	 * Find the {@link Permissions} object by the given shortcut.
	 *
	 * @param shortcut
	 *            the shortcut
	 * @return the found {@link Permissions} object or null if not.
	 */
	Optional<Permissions> findByShortcut(final @NonNull String shortcut);

	/**
	 * Factory method to create and save a new {@link Permissions} object.
	 *
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 * @return the new {@link Permissions} object.
	 */
	Permissions save(final @NonNull String name, final @NonNull String description);

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
	Permissions save(final @NonNull String name, final @NonNull String description,
		final @NonNull String shortcut);
}