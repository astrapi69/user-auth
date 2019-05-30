package de.alpharogroup.user.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.repositories.PermissionsRepository;
import de.alpharogroup.user.auth.service.api.PermissionsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionsServiceImpl implements PermissionsService
{

	PermissionsRepository permissionsRepository;

	@Override
	public Permissions save(String name, String description)
	{
		Permissions permissions = permissionsRepository.findByName(name);
		if (permissions != null)
		{
			return permissions;
		}
		return permissionsRepository
			.save(Permissions.builder().name(name).description(description).build());
	}

	@Override
	public Permissions createAndSavePermissions(String name, String description, String shortcut)
	{
		Permissions permissions = permissionsRepository.findByName(name);
		if (permissions != null)
		{
			return permissions;
		}
		return permissionsRepository.save(
			Permissions.builder().name(name).description(description).shortcut(shortcut).build());
	}

	@Override
	public List<Permissions> find(String description, String permissionName, String shortcut)
	{
		return permissionsRepository.find(description, permissionName, shortcut);
	}

	@Override
	public Permissions findByName(String name)
	{
		return permissionsRepository.findByName(name);
	}

	@Override
	public Permissions findByShortcut(String shortcut)
	{
		return permissionsRepository.findByShortcut(shortcut);
	}

}
