package de.alpharogroup.user.auth.mapper;


import de.alpharogroup.user.auth.dto.Contactmethod;
import de.alpharogroup.user.auth.enums.ContactmethodType;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactmethodMapperTest
{
	@Test
	public void testMapper(){
		ContactmethodMapper mapper = new ContactmethodMapper();
		Contactmethods entity = Contactmethods.builder()
			.contactmethod(ContactmethodType.EMAIL)
			.contactvalue("foo@bar.org")
			.build();
		Contactmethod dto = mapper.toDto(entity);
		assertEquals(dto.getContactvalue(), entity.getContactvalue());
	}

}
