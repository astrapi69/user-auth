package de.alpharogroup.user.auth.service;

import java.util.Optional;
import java.util.UUID;

import de.alpharogroup.spring.service.api.GenericService;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import de.alpharogroup.user.auth.jpa.repositories.ContactmethodsRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.repositories.PermissionsRepository;
import de.alpharogroup.user.auth.service.api.PermissionsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionsServiceImpl implements
	GenericService<Permissions, UUID, PermissionsRepository>,
	PermissionsService
{

	PermissionsRepository repository;

	@Override
	public Optional<Permissions> findByName(String name)
	{
		return repository.findByName(name);
	}

	@Override
	public Optional<Permissions> findByShortcut(String shortcut)
	{
		return repository.findByShortcut(shortcut);
	}

	@Override
	public Permissions save(String name, String description)
	{
		Optional<Permissions> optional = repository.findByName(name);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return repository
			.save(Permissions.builder().name(name).description(description).build());
	}

	@Override
	public Permissions save(String name, String description, String shortcut)
	{
		Optional<Permissions> optional = repository.findByName(name);
		if (optional.isPresent())
		{
			return optional.get();
		}
		return repository.save(
			Permissions.builder().name(name).description(description).shortcut(shortcut).build());
	}

}
