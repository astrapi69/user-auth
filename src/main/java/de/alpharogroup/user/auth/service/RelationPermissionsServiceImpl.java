package de.alpharogroup.user.auth.service;

import java.util.List;

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
		// TODO Auto-generated method stub

	}

	@Override
	public List<RelationPermissions> find(Users provider, Users subscriber)
	{
		return relationPermissionsRepository.find(provider, subscriber);
	}

	@Override
	public List<RelationPermissions> find(Users provider, Users subscriber, Permissions permission)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationPermissions findRelationPermissions(Users provider, Users subscriber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationPermissions findRelationPermissions(Users provider, Users subscriber,
		Permissions permission)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllPermissions(Users provider, Users subscriber)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removePermission(Users provider, Users subscriber, Permissions permission)
	{
		// TODO Auto-generated method stub

	}

}
