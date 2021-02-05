/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
