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
package io.github.astrapi69.user.auth.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import io.github.astrapi69.user.auth.mapper.ResetPasswordMapper;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.user.auth.dto.ResetPassword;
import io.github.astrapi69.user.auth.jpa.entities.ResetPasswords;

public class ResetPasswordMapperTest
{
	@Test
	public void testMapper()
	{
		ResetPasswordMapper mapper = new ResetPasswordMapper();
		ResetPasswords entity = ResetPasswords.builder().id(UUID.randomUUID()).build();
		ResetPassword dto = mapper.toDto(entity);
		assertEquals(dto.getId(), entity.getId());
	}
}
