package io.github.astrapi69.user.auth.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.user.auth.enums.ContactmethodType;

class ContactmethodTypeConverterTest
{

	private ContactmethodTypeConverter converter;

	@BeforeEach
	void setUp()
	{
		converter = new ContactmethodTypeConverter();
	}

	@Test
	void testConvertToDatabaseColumn()
	{
		// Test converting enum to string
		assertEquals("EMAIL", converter.convertToDatabaseColumn(ContactmethodType.EMAIL));
		assertEquals("MAIL", converter.convertToDatabaseColumn(ContactmethodType.MAIL));
		assertEquals("TELEFON", converter.convertToDatabaseColumn(ContactmethodType.TELEFON));
		assertEquals("FAX", converter.convertToDatabaseColumn(ContactmethodType.FAX));
		assertEquals("MOBILE", converter.convertToDatabaseColumn(ContactmethodType.MOBILE));
		assertEquals("SMS", converter.convertToDatabaseColumn(ContactmethodType.SMS));
		assertEquals("MESSENGER", converter.convertToDatabaseColumn(ContactmethodType.MESSENGER));
		assertEquals("INTERNET", converter.convertToDatabaseColumn(ContactmethodType.INTERNET));
		assertEquals("NEWSGROUP", converter.convertToDatabaseColumn(ContactmethodType.NEWSGROUP));

		// Test null value
		assertNull(converter.convertToDatabaseColumn(null));
	}

	@Test
	void testConvertToEntityAttribute()
	{
		// Test converting string to enum
		assertEquals(ContactmethodType.EMAIL, converter.convertToEntityAttribute("EMAIL"));
		assertEquals(ContactmethodType.MAIL, converter.convertToEntityAttribute("MAIL"));
		assertEquals(ContactmethodType.TELEFON, converter.convertToEntityAttribute("TELEFON"));
		assertEquals(ContactmethodType.FAX, converter.convertToEntityAttribute("FAX"));
		assertEquals(ContactmethodType.MOBILE, converter.convertToEntityAttribute("MOBILE"));
		assertEquals(ContactmethodType.SMS, converter.convertToEntityAttribute("SMS"));
		assertEquals(ContactmethodType.MESSENGER, converter.convertToEntityAttribute("MESSENGER"));
		assertEquals(ContactmethodType.INTERNET, converter.convertToEntityAttribute("INTERNET"));
		assertEquals(ContactmethodType.NEWSGROUP, converter.convertToEntityAttribute("NEWSGROUP"));

		// Test null value
		assertNull(converter.convertToEntityAttribute(null));
	}

	@Test
	void testInvalidEnumValue()
	{
		// Test that invalid string input returns null or throws an exception
		assertThrows(IllegalArgumentException.class,
			() -> converter.convertToEntityAttribute("INVALID_ENUM"));
	}
}
