package de.alpharogroup.user.auth.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RelationPermissionsRepository extends JpaRepository<RelationPermissions, Long>
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
