package de.alpharogroup.user.auth.service.api;

import java.util.Optional;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import de.alpharogroup.user.auth.jpa.entities.Users;
import lombok.NonNull;

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
	void addPermission(final @NonNull Users provider, final @NonNull Users subscriber,
		final @NonNull Permissions permission);

	/**
	 * Find a list of RelationPermissions that the given provider granted to the subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @return the list
	 */
	Optional<RelationPermissions> find(final @NonNull Users provider,
		final @NonNull Users subscriber);

	/**
	 * Checks if the given subscriber have the given permission to the given provider
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @param permission
	 *            the permission
	 * @return true if the given subscriber have the given permission otherwise false
	 */
	boolean havePermission(final @NonNull Users provider, final @NonNull Users subscriber,
		final @NonNull Permissions permission);

	/**
	 * Removes all permissions that are given for both users.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 */
	void removeAllPermissions(final @NonNull Users provider, final @NonNull Users subscriber);

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
	void removePermission(final @NonNull Users provider, final @NonNull Users subscriber,
		final @NonNull Permissions permission);

}