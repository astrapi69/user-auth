package de.alpharogroup.user.auth.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.alpharogroup.user.auth.dto.ResetPassword;
import de.alpharogroup.user.auth.jpa.entities.ResetPasswords;

import java.util.UUID;

public class ResetPasswordMapperTest
{
	@Test
	public void testMapper()
	{
		ResetPasswordMapper mapper = new ResetPasswordMapper();
		ResetPasswords entity = ResetPasswords.builder()
			.id(UUID.randomUUID())
			.build();
		ResetPassword dto = mapper.toDto(entity);
		assertEquals(dto.getId(), entity.getId());
	}
}
