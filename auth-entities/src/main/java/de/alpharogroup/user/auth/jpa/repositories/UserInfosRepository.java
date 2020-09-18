package de.alpharogroup.user.auth.jpa.repositories;

import java.util.UUID;

import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface UserInfosRepository extends JpaRepository<UserInfos, UUID>
{
	UserInfos findByOwner(Users owner);
}
