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
package io.github.astrapi69.user.auth.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.astrapi69.user.auth.jpa.entities.Permissions;
import io.github.astrapi69.user.auth.jpa.entities.RelationPermissions;
import io.github.astrapi69.user.auth.jpa.entities.Users;

@Repository
public interface RelationPermissionsRepository extends JpaRepository<RelationPermissions, UUID>
{

	/**
	 * Find a list of RelationPermissions that the given provider granted to the subscriber.
	 *
	 * @param provider
	 *            the provider
	 * @param subscriber
	 *            the subscriber
	 * @return an optional with the entry if found
	 */
	Optional<RelationPermissions> findByProviderAndSubscriber(final Users provider,
		final Users subscriber);

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
	 * @return an optional with the entry if found
	 */
	@Transactional
	@Query("select rp from RelationPermissions rp where rp.provider = :provider and rp.subscriber = :subscriber and :permission in (rp.permissions)")
	Optional<RelationPermissions> findByProviderAndSubscriberAndPermission(
		@Param("provider") final Users provider, @Param("subscriber") final Users subscriber,
		@Param("permission") Permissions permission);

}
