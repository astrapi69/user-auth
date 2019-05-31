package de.alpharogroup.user.auth.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>
{

	/**
	 * Checks if an {@link Roles} object exists with the given role name.
	 *
	 * @param name
	 *            the role name
	 * @return true if a {@link Roles} object exists with the given role name
	 */
	boolean existsByName(final String name);

	/**
	 * Find the {@link Roles} object with the given role name.
	 *
	 * @param name
	 *            the role name
	 * @return an optional with the entry if found
	 */
	Optional<Roles> findByName(final String name);
}
