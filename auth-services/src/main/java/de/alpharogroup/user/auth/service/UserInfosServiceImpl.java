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

import de.alpharogroup.spring.service.api.GenericService;
import de.alpharogroup.user.auth.jpa.entities.UserInfos;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.UserInfosRepository;
import de.alpharogroup.user.auth.service.api.UserInfosService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInfosServiceImpl implements GenericService<UserInfos, UUID, UserInfosRepository>,
	UserInfosService
{
	UserInfosRepository repository;

	@Override public UserInfos findByOwner(Users user)
	{
		return repository.findByOwner(user);
	}

	public String getFullName(Users user) {
		String fullName = getFullName(findByOwner(user));
		return fullName.isEmpty() ? user.getUsername() : fullName;
	}
}
