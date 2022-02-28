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

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import io.github.astrapi69.crypto.pw.PasswordEncryptor;
import io.github.astrapi69.resourcebundle.locale.LocaleResolver;
import io.github.astrapi69.spring.service.api.GenericService;
import io.github.astrapi69.user.auth.dto.ResetPassword;
import io.github.astrapi69.user.auth.dto.ResetPasswordMessage;
import io.github.astrapi69.user.auth.enums.ResetPasswordRest;
import io.github.astrapi69.user.auth.enums.Rest;
import io.github.astrapi69.user.auth.jpa.entities.ResetPasswords;
import io.github.astrapi69.user.auth.jpa.entities.UserInfos;
import io.github.astrapi69.user.auth.jpa.entities.Users;
import io.github.astrapi69.user.auth.jpa.repositories.ResetPasswordsRepository;
import io.github.astrapi69.user.auth.mapper.ResetPasswordMapper;
import io.github.astrapi69.user.auth.service.api.ResetPasswordsService;
import io.github.astrapi69.user.auth.service.api.UserInfosService;
import io.github.astrapi69.user.auth.service.api.UsersService;
import io.github.astrapi69.message.mail.utils.EmailComposer;
import io.github.astrapi69.message.mail.viewmodel.InfoMessage;
import io.github.astrapi69.throwable.RuntimeExceptionDecorator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

@Service
@Getter
@AllArgsConstructor
@Log
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPasswordsServiceImpl
	implements
		GenericService<ResetPasswords, UUID, ResetPasswordsRepository>,
		ResetPasswordsService
{

	UsersService usersService;
	UserInfosService userInfosService;
	ResetPasswordsRepository repository;
	ResetPasswordMapper resetPasswordMapper;

	@Override
	public Optional<ResetPasswords> findByUser(Users user)
	{
		return repository.findByUser(user);
	}

	@Override
	public Optional<ResetPasswords> findByUserAndGeneratedPassword(Users user,
		String generatedPassword)
	{
		return repository.findByUserAndGeneratedPassword(user, generatedPassword);
	}

	@Override
	public ResetPasswordMessage generateResetPasswordMessageForMail(final @NonNull String email,
		final @NonNull String contextPath)
	{
		ResetPasswordMessage resetPasswordMessage = ResetPasswordMessage.builder().build();
		// 1. Check if email exists.
		Optional<Users> optionalUser = usersService.findByEmail(email);
		// 2. if email exists...
		if (!optionalUser.isPresent())
		{
			throw new RuntimeException("No user exists with the given email");
		}
		else
		{
			Users user = optionalUser.get();

			Optional<ResetPasswords> optionalResetPasswords = findByUser(user);
			ResetPasswords resetPassword;
			String newPassword = null;
			if (optionalResetPasswords.isPresent())
			{
				PasswordEncryptor passwordService = PasswordEncryptor.getInstance();
				newPassword = passwordService.getRandomPassword(8);
				final String tmpNewPw = newPassword;
				String hashedPassword = RuntimeExceptionDecorator
					.decorate(() -> passwordService.hashAndHexPassword(tmpNewPw, user.getSalt()));
				resetPassword = ResetPasswords.builder().build();
				resetPassword.setGeneratedPassword(hashedPassword);
			}
			else
			{
				resetPassword = optionalResetPasswords.get();
			}
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expiryDate = now.plusDays(1);
			resetPassword.setExpiryDate(expiryDate);
			resetPassword.setStarttime(now);

			ResetPasswords saved = repository.save(resetPassword);
			ResetPassword dto = resetPasswordMapper.toDto(saved);
			resetPasswordMessage.setResetPassword(dto);

			String applicationDomainName = contextPath;
			String applicationSenderAddress = user.getApplications().getEmail();
			String recipientEmailContact = user.getApplications().getEmail();
			String usersEmail = user.getEmail();
			resetPasswordMessage.setApplicationDomainName(applicationDomainName);
			resetPasswordMessage.setApplicationSenderAddress(applicationSenderAddress);
			resetPasswordMessage.setUsersEmail(usersEmail);

			UserInfos userInfos = userInfosService.findByOwner(user);
			String locale = userInfos.getLocale();
			Locale usersLocale = LocaleResolver.resolveLocaleCode(locale);
			String urlForForgottenPassword = getUrlForForgottenPassword(contextPath, dto);
			InfoMessage infoMessageModel = EmailComposer.createEmailMessageForForgottenPassword(
				applicationSenderAddress, applicationDomainName, user.getUsername(),
				userInfosService.getFullName(user), recipientEmailContact, newPassword,
				urlForForgottenPassword, usersLocale);

			try
			{
				// TODO refactor with commons-email
				// For now no email is send!!!
				SendMessageService.sendInfoEmail(SendEmailProvider.getEmailSender(),
					infoMessageModel);
			}
			catch (MessagingException e)
			{
				log.log(Level.ALL, "MessagingException by send email for forgotten pw. For user:"
					+ user.getUsername(), e);
			}
			catch (Throwable t)
			{
				log.log(Level.ALL,
					"Error by send email for forgotten pw. For user:" + user.getUsername(), t);
			}
		}
		return resetPasswordMessage;
	}

	protected String getUrlForForgottenPassword(String contextPath, ResetPassword dto)
	{
		String urlForForgottenPassword = contextPath + Rest.VERSION_1 + ResetPasswordRest.MAIN_PATH
			+ ResetPasswordRest.VERIFY_TOKEN_PATH + "/?token=" + dto.getId();
		return urlForForgottenPassword;
	}

	private String generateNewPassword(String salt)
	{
		PasswordEncryptor passwordService = PasswordEncryptor.getInstance();
		String newPassword = passwordService.getRandomPassword(8);
		return RuntimeExceptionDecorator
			.decorate(() -> passwordService.hashAndHexPassword(newPassword, salt));
	}

}
