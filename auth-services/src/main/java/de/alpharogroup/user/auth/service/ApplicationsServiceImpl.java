package de.alpharogroup.user.auth.service;

import de.alpharogroup.spring.service.api.GenericService;
import de.alpharogroup.user.auth.jpa.entities.Applications;
import de.alpharogroup.user.auth.jpa.repositories.ApplicationsRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class ApplicationsServiceImpl
	implements GenericService<Applications, UUID, ApplicationsRepository>
{
	ApplicationsRepository repository;

	public Optional<Applications> findByName(final String name) {
		return repository.findByName(name);
	}
}
