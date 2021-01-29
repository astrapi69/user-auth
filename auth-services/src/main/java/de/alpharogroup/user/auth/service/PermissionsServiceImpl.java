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
