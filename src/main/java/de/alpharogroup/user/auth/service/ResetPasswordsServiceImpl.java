package de.alpharogroup.user.auth.service;

import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.ResetPasswordsRepository;
import de.alpharogroup.user.auth.service.api.ResetPasswordsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPasswordsServiceImpl implements ResetPasswordsService
{

	ResetPasswordsRepository resetPasswordsRepository;

	@Override
	public ResetPasswords findResetPassword(Users user)
	{
		return resetPasswordsRepository.findResetPassword(user);
	}

	@Override
	public ResetPasswords findResetPassword(Users user, String generatedPassword)
	{
		return resetPasswordsRepository.findResetPassword(user, generatedPassword);
	}

}
