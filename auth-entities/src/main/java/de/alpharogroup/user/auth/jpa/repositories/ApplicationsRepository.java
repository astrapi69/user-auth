package de.alpharogroup.user.auth.jpa.repositories;

import de.alpharogroup.user.auth.jpa.entities.Applications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository public interface ApplicationsRepository extends JpaRepository<Applications, UUID>
{

	/**
	 * Checks if an {@link Applications} object exists with the given role name.
	 *
	 * @param name the role name
	 * @return true if a {@link Applications} object exists with the given role name
	 */
	boolean existsByName(final String name);

	/**
	 * Find the {@link Applications} object with the given role name.
	 *
	 * @param name the role name
	 * @return an optional with the entry if found
	 */
	Optional<Applications> findByName(final String name);
}
