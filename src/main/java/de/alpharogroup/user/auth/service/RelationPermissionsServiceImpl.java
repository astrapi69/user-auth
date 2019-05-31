package de.alpharogroup.user.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.RelationPermissions;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.RelationPermissionsRepository;
import de.alpharogroup.user.auth.service.api.RelationPermissionsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RelationPermissionsServiceImpl implements RelationPermissionsService
{

	RelationPermissionsRepository relationPermissionsRepository;

	@Override
	public void addPermission(Users provider, Users subscriber, Permissions permission)
	{
		Optional<RelationPermissions> optional = relationPermissionsRepository.findByProviderAndSubscriber(provider,
			subscriber);
		RelationPermissions relationPermissions;
		if (!optional.isPresent())
		{
			relationPermissions = RelationPermissions.builder().provider(provider)
				.subscriber(subscriber).build();
		}
		else
		{
			relationPermissions = optional.get();
		}
		relationPermissions.getPermissions().add(permission);
		relationPermissionsRepository.save(relationPermissions);
	}

	@Override
	public Optional<RelationPermissions> find(Users provider, Users subscriber)
	{
		return relationPermissionsRepository.findByProviderAndSubscriber(provider, subscriber);
	}


	@Override
	public boolean havePermission(Users provider, Users subscriber, Permissions permission)
	{
		Optional<RelationPermissions> optional = relationPermissionsRepository.findByProviderAndSubscriber(provider,
			subscriber);
		if (optional.isPresent())
		{
			return optional.get().getPermissions().contains(permission);
		}
		return false;
	}

	@Override
	public void removeAllPermissions(Users provider, Users subscriber)
	{
		Optional<RelationPermissions> optional = relationPermissionsRepository.findByProviderAndSubscriber(provider,
			subscriber);
		RelationPermissions relationPermissions;
		if (optional.isPresent())
		{
			relationPermissions = optional.get();
			relationPermissions.getPermissions().clear();
		}
	}

	@Override
	public void removePermission(Users provider, Users subscriber, Permissions permission)
	{
		Optional<RelationPermissions> optional = relationPermissionsRepository.findByProviderAndSubscriber(provider,
			subscriber);
		RelationPermissions relationPermissions;
		if (optional.isPresent())
		{
			relationPermissions = optional.get();
			relationPermissions.getPermissions().remove(permission);
		}
	}

}
