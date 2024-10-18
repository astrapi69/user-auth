package io.github.astrapi69.user.auth.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.user.auth.enums.GenderType;

class GenderTypeConverterTest
{

	private final GenderTypeConverter converter = new GenderTypeConverter();

	@Test
	void testConvertToDatabaseColumn()
	{
		// Test converting enum to string
		assertEquals("FEMALE", converter.convertToDatabaseColumn(GenderType.FEMALE));
		assertEquals("INCORPORATION", converter.convertToDatabaseColumn(GenderType.INCORPORATION));
		assertEquals("MALE", converter.convertToDatabaseColumn(GenderType.MALE));
		assertEquals("UNDEFINED", converter.convertToDatabaseColumn(GenderType.UNDEFINED));

		// Test null value conversion
		assertNull(converter.convertToDatabaseColumn(null));
	}

	@Test
	void testConvertToEntityAttribute()
	{
		// Test converting string to enum
		assertEquals(GenderType.FEMALE, converter.convertToEntityAttribute("FEMALE"));
		assertEquals(GenderType.INCORPORATION, converter.convertToEntityAttribute("INCORPORATION"));
		assertEquals(GenderType.MALE, converter.convertToEntityAttribute("MALE"));
		assertEquals(GenderType.UNDEFINED, converter.convertToEntityAttribute("UNDEFINED"));

		// Test null value conversion
		assertNull(converter.convertToEntityAttribute(null));

		// Test invalid enum value conversion (should throw IllegalArgumentException)
		assertThrows(IllegalArgumentException.class,
			() -> converter.convertToEntityAttribute("INVALID_ENUM"));
	}
}
