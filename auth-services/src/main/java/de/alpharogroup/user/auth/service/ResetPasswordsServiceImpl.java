package de.alpharogroup.user.auth.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;

import de.alpharogroup.crypto.pw.PasswordEncryptor;
import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.dto.ResetPasswordMessage;
import de.alpharogroup.user.auth.mapper.ResetPasswordMapper;
import de.alpharogroup.user.auth.service.api.UsersService;
import io.github.astrapi69.throwable.RuntimeExceptionDecorator;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.jpa.repositories.ResetPasswordsRepository;
import de.alpharogroup.user.auth.service.api.ResetPasswordsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Service
@AllArgsConstructor
@Log
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPasswordsServiceImpl implements ResetPasswordsService
{

	UsersService usersService;
	ResetPasswordsRepository resetPasswordsRepository;
	ResetPasswordMapper resetPasswordMapper;

	@Override
	public Optional<ResetPasswords> findByUser(Users user)
	{
		return resetPasswordsRepository.findByUser(user);
	}

	@Override
	public Optional<ResetPasswords> findByUserAndGeneratedPassword(Users user,
		String generatedPassword)
	{
		return resetPasswordsRepository.findByUserAndGeneratedPassword(user, generatedPassword);
	}

	public ResetPasswordMessage generateResetPasswordMessageForMail(String email) {
		ResetPasswordMessage resetPasswordMessage = ResetPasswordMessage.builder().build();
		// 1. Check if email exists.
		Optional<Users> optionalUser = usersService.findByEmail(email);
		// 2. if email exists...
		if(optionalUser.isPresent()) {
			Users user = optionalUser.get();

			Optional<ResetPasswords> optionalResetPasswords = findByUser(user);
			ResetPasswords resetPassword;
			if(!optionalResetPasswords.isPresent()) {
				String hashedPassword = generateNewPassword(user.getSalt());
				resetPassword = ResetPasswords.builder().build();
				resetPassword.setGeneratedPassword(hashedPassword);
			} else {
				resetPassword = optionalResetPasswords.get();
			}
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expiryDate = now.plusDays(1);
			resetPassword.setExpiryDate(expiryDate);
			resetPassword.setStarttime(now);

			ResetPasswords saved = resetPasswordsRepository.save(resetPassword);
			ResetPassword dto = resetPasswordMapper.toDto(saved);
			resetPasswordMessage.setResetPassword(dto);


			String applicationDomainName = user.getApplications().getDomainName();
			String applicationSenderAddress = user.getApplications().getEmail();
			String usersEmail = user.getEmail();
			// TODO
			String urlForForgottenPassword = null;

		} else {

		}
		return resetPasswordMessage;
	}

	private String generateNewPassword(String salt)
	{
		PasswordEncryptor passwordService = PasswordEncryptor.getInstance();
		String newPassword = passwordService.getRandomPassword(8);
		return RuntimeExceptionDecorator.decorate(()-> passwordService.hashAndHexPassword(
			newPassword, salt));
	}

}
