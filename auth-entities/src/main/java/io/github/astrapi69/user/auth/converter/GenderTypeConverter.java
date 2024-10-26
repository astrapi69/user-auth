package io.github.astrapi69.user.auth.converter;

import io.github.astrapi69.user.auth.enums.GenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, String>
{

	@Override
	public String convertToDatabaseColumn(GenderType genderType)
	{
		if (genderType == null)
		{
			return null;
		}
		return genderType.name(); // Store the enum as its string name in the database
	}

	@Override
	public GenderType convertToEntityAttribute(String dbData)
	{
		if (dbData == null)
		{
			return null;
		}
		return GenderType.valueOf(dbData); // Convert database value back to enum
	}
}
