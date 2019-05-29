package de.alpharogroup.user.auth.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Integer> {

}
