package de.alpharogroup.user.auth.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	public boolean existsByName(String name)
	{
		return rolesRepository.existsByName(name);
	}

	@Override
	public Optional<Roles> findByName(String name)
	{
		return rolesRepository.findByName(name);
	}

	@Override
	public Roles save(String name, String description)
	{
		return rolesRepository.save(Roles.builder().name(name).description(description).build());
	}

	@Override
	public Roles save(String name, String description, Set<Permissions> permissions)
	{
		return rolesRepository.save(
			Roles.builder().name(name).description(description).permissions(permissions).build());
	}

	@Override
	public Set<Roles> getRoles(Set<String> stringRoles) {
		return stringRoles.stream()
			.filter(s -> existsByName(s))
			.map(strRole -> findByName(strRole).get()).collect(Collectors.toSet());
	}

}
