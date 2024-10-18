package io.github.astrapi69.user.auth.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import io.github.astrapi69.user.auth.enums.ContactmethodType;

@Converter(autoApply = true)
public class ContactmethodTypeConverter implements AttributeConverter<ContactmethodType, String>
{

	@Override
	public String convertToDatabaseColumn(ContactmethodType contactmethodType)
	{
		if (contactmethodType == null)
		{
			return null;
		}
		return contactmethodType.name(); // This stores the enum's name in the database
	}

	@Override
	public ContactmethodType convertToEntityAttribute(String dbData)
	{
		if (dbData == null)
		{
			return null;
		}
		return ContactmethodType.valueOf(dbData); // This converts the database value back to the
													// enum
	}
}
