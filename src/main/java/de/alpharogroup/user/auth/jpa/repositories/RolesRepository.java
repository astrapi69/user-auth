package de.alpharogroup.user.auth.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

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
