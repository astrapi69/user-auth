/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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