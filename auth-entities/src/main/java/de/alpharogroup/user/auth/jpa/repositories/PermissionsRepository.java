package de.alpharogroup.user.auth.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID>
{

	/**
	 * Find the {@link Permissions} object by the given name.
	 *
	 * @param name
	 *            the name
	 * @return an optional with the entry if found
	 */
	Optional<Permissions> findByName(String name);

	/**
	 * Find the {@link Permissions} object by the given shortcut.
	 *
	 * @param shortcut
	 *            the shortcut
	 * @return an optional with the entry if found
	 */
	Optional<Permissions> findByShortcut(String shortcut);
}
