package de.alpharogroup.user.auth.service.api;

import java.util.List;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import de.alpharogroup.user.auth.jpa.entities.Users;

/**
 * The interface {@link RelationPermissionsService}
 */
public interface RelationPermissionsService
{

	/**
	 * Adds the given permission for the given subscriber provided from the provider.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @param permission
	 *            the permission
	 */
	void addPermission(Users provider, Users subscriber, Permissions permission);

	/**
	 * Find a list of RelationPermissions that the given provider granted to the subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @return the list
	 */
	List<RelationPermissions> find(final Users provider, final Users subscriber);

	/**
	 * Find a list of RelationPermissions from the given provider and to the subscriber and the
	 * given permission if the provider granted this permission to the subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @param permission
	 *            the permission
	 * @return the list
	 */
	List<RelationPermissions> find(final Users provider, final Users subscriber,
		Permissions permission);

	/**
	 * Find all given permissions that the given provider granted to the subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @return the list
	 */
	RelationPermissions findRelationPermissions(final Users provider, final Users subscriber);

	/**
	 * Finds the RelationPermissions object from the given permissions the given provider and the
	 * subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @param permission
	 *            the permission
	 * @return the relation permissions
	 */
	RelationPermissions findRelationPermissions(final Users provider, final Users subscriber,
		Permissions permission);

	/**
	 * Removes all permissions that are given for both users.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 */
	void removeAllPermissions(Users provider, Users subscriber);

	/**
	 * Removes the given permission for the given subscriber provided from the provider.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @param permission
	 *            the permission
	 */
	void removePermission(Users provider, Users subscriber, Permissions permission);

}