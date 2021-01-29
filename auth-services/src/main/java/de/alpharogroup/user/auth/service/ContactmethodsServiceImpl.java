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

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.spring.service.api.GenericService;
import de.alpharogroup.user.auth.enums.ContactmethodType;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.ContactmethodsRepository;
import de.alpharogroup.user.auth.service.api.ContactmethodsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Transactional
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class ContactmethodsServiceImpl
	implements
		GenericService<Contactmethods, UUID, ContactmethodsRepository>,
		ContactmethodsService
{
	ContactmethodsRepository repository;

	public boolean compare(final Contactmethods contact, final Contactmethods compare)
	{
		if (contact == null && compare != null)
		{
			return false;
		}
		if (contact == null && compare == null)
		{
			return true;
		}
		if (contact != null && compare == null)
		{
			return false;
		}
		if (contact.getContactvalue() == null && compare.getContactvalue() != null)
		{
			return false;
		}
		if (contact.getContactvalue() == null && compare.getContactvalue() == null)
		{
			return true;
		}
		if (contact.getContactvalue() != null && compare.getContactvalue() == null)
		{
			return false;
		}
		return contact.getContactmethod().equals(compare.getContactmethod())
			&& contact.getContactvalue().equals(compare.getContactvalue());
	}

	@Override
	public boolean existsContact(Contactmethods contact)
	{
		if (contact != null)
		{
			return repository.existsById(contact.getId());
		}
		return false;
	}

	@Override
	public boolean existsContact(String contactValue, ContactmethodType contactMethod)
	{
		List<Contactmethods> contacts = repository.find(contactMethod, contactValue);
		if (null != contacts && !contacts.isEmpty())
		{
			return true;
		}
		return false;
	}

	@Override
	public List<Contactmethods> findContact(String contactValue, ContactmethodType contactMethod)
	{
		return repository.find(contactMethod, contactValue);
	}

	@Override
	public List<Contactmethods> findContactmethod(ContactmethodType contactmethod, Users user)
	{
		return repository.findContactmethods(contactmethod, user);
	}
}
