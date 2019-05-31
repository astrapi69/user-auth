package de.alpharogroup.user.auth.service;

import java.util.Optional;

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
	public Optional<Permissions> findByName(String name)
	{
		return permissionsRepository.findByName(name);
	}

	@Override
	public Optional<Permissions> findByShortcut(String shortcut)
	{
		return permissionsRepository.findByShortcut(shortcut);
	}

	@Override
	public Permissions save(String name, String description)
	{
		Optional<Permissions> optional = permissionsRepository.findByName(name);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return permissionsRepository
			.save(Permissions.builder().name(name).description(description).build());
	}

	@Override
	public Permissions save(String name, String description, String shortcut)
	{
		Optional<Permissions> optional = permissionsRepository.findByName(name);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return permissionsRepository.save(
			Permissions.builder().name(name).description(description).shortcut(shortcut).build());
	}

}
