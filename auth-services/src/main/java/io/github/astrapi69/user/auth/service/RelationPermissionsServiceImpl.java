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
package io.github.astrapi69.user.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.astrapi69.spring.service.api.GenericService;
import io.github.astrapi69.user.auth.jpa.entities.Permissions;
import io.github.astrapi69.user.auth.jpa.entities.RelationPermissions;
import io.github.astrapi69.user.auth.jpa.entities.Users;
import io.github.astrapi69.user.auth.jpa.repositories.RelationPermissionsRepository;
import io.github.astrapi69.user.auth.service.api.RelationPermissionsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Service
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RelationPermissionsServiceImpl
	implements

		GenericService<RelationPermissions, UUID, RelationPermissionsRepository>,
		RelationPermissionsService
{

	RelationPermissionsRepository repository;

	@Override
	public void addPermission(Users provider, Users subscriber, Permissions permission)
	{
		Optional<RelationPermissions> optional = repository.findByProviderAndSubscriber(provider,
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
		repository.save(relationPermissions);
	}

	@Override
	public Optional<RelationPermissions> find(Users provider, Users subscriber)
	{
		return repository.findByProviderAndSubscriber(provider, subscriber);
	}


	@Override
	public boolean havePermission(Users provider, Users subscriber, Permissions permission)
	{
		Optional<RelationPermissions> optional = repository.findByProviderAndSubscriber(provider,
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
		Optional<RelationPermissions> optional = repository.findByProviderAndSubscriber(provider,
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
		Optional<RelationPermissions> optional = repository.findByProviderAndSubscriber(provider,
			subscriber);
		RelationPermissions relationPermissions;
		if (optional.isPresent())
		{
			relationPermissions = optional.get();
			relationPermissions.getPermissions().remove(permission);
		}
	}

}
