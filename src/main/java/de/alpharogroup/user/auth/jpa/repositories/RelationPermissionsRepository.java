package de.alpharogroup.user.auth.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;

@Repository
public interface RelationPermissionsRepository extends JpaRepository<RelationPermissions, Integer> {

}
