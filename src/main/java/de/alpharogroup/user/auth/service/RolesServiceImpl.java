package de.alpharogroup.user.auth.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.repositories.RolesRepository;
import de.alpharogroup.user.auth.service.api.RolesService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolesServiceImpl implements RolesService
{
	RolesRepository rolesRepository;

	@Override
	public Roles createAndSaveRole(String rolename, String description)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Roles createAndSaveRole(String rolename, String description,
		Set<Permissions> permissions)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String rolename)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Permissions> findAllPermissions(Roles role)
	{
		return rolesRepository.findAllPermissions(role);
	}

	@Override
	public Roles findRole(String rolename)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Roles> findRoles(String rolename)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
