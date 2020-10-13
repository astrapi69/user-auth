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
import de.alpharogroup.user.auth.service.api.UsersService;
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

	public Optional<ResetPasswords> generateResetPasswordMessageForMail(String email) {
		Optional<Users> optionalUser = usersService.findByEmail(email);
		if(optionalUser.isPresent()) {
			Users user = optionalUser.get();
			Optional<ResetPasswords> optionalResetPasswords = findByUser(user);
			Optional<String> hashedPassword = generateNewPassword(user.getSalt());
			String password = "";
			if(hashedPassword.isPresent()) {
				password = hashedPassword.get();
			} else {
				log.log(Level.SEVERE, "!!!=======================================!!!");
				log.log(Level.SEVERE, "!!!===[PASSWORD COULD NOT BE GENERATED]" +
					" for email:" + email +
					"===!!!");
				log.log(Level.SEVERE, "!!!=======================================!!!");
				return Optional.empty();
			}
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expiryDate = now.plusDays(1);
			ResetPasswords resetPassword;
			if(optionalResetPasswords.isPresent()) {
				resetPassword = optionalResetPasswords.get();
			} else {
				resetPassword = ResetPasswords.builder().build();
			}
			resetPassword.setExpiryDate(expiryDate);
			resetPassword.setStarttime(now);
			resetPassword.setGeneratedPassword(password);
			ResetPasswords saved = resetPasswordsRepository.save(resetPassword);
			return Optional.of(saved);
		}
		return Optional.empty();
	}

	private Optional<String> generateNewPassword(String salt)
	{
		PasswordEncryptor passwordService = PasswordEncryptor.getInstance();
		String newPassword = passwordService.getRandomPassword(8);
		try {
			return Optional.of(passwordService.hashAndHexPassword(
				newPassword, salt));
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
			| InvalidKeyException | InvalidKeySpecException | UnsupportedEncodingException
			| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e)
		{
			return Optional.empty();
		}
	}

}
