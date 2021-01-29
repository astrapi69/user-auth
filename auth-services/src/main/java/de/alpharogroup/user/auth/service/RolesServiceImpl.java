package de.alpharogroup.user.auth.service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import de.alpharogroup.spring.service.api.GenericService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.Permissions;
import de.alpharogroup.user.auth.jpa.entities.Roles;
import de.alpharogroup.user.auth.jpa.repositories.RolesRepository;
import de.alpharogroup.user.auth.service.api.RolesService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolesServiceImpl implements

	GenericService<Roles, UUID, RolesRepository>,
	RolesService
{
	RolesRepository repository;

	@Override
	public boolean existsByName(String name)
	{
		return repository.existsByName(name);
	}

	@Override
	public Optional<Roles> findByName(String name)
	{
		return repository.findByName(name);
	}

	@Override
	public Roles save(String name, String description)
	{
		return repository.save(Roles.builder().name(name).description(description).build());
	}

	@Override
	public Roles save(String name, String description, Set<Permissions> permissions)
	{
		return repository.save(
			Roles.builder().name(name).description(description).permissions(permissions).build());
	}

	@Override
	public Set<Roles> getRoles(Set<String> stringRoles) {
		return stringRoles.stream()
			.filter(s -> existsByName(s))
			.map(strRole -> findByName(strRole).get()).collect(Collectors.toSet());
	}

}
