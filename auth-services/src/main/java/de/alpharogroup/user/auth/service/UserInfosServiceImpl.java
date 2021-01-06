package de.alpharogroup.user.auth.service;

import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UserInfosRepository;
import de.alpharogroup.user.auth.service.api.UserInfosService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInfosServiceImpl implements UserInfosService
{
	UserInfosRepository repository;

	@Override public UserInfos findByOwner(Users user)
	{
		return repository.findByOwner(user);
	}

	public String getFullName(Users user) {
		String fullName = getFullName(findByOwner(user));
		return fullName.isEmpty() ? user.getUsername() : fullName;
	}
}
