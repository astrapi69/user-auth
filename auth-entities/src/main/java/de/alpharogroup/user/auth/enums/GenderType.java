package de.alpharogroup.user.auth.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The enum {@link GenderType} defines the type of the gender.
 */
public enum GenderType
{

	/** The constant for the gender female. */
	FEMALE,
	/** The constant for the gender that represents a company. */
	INCORPORATION,
	/** The constant for the gender male. */
	MALE,
	/** The constant if the gender is undefined. */
	UNDEFINED;

	/**
	 * Gets the all genders without the gender type {@link GenderType#UNDEFINED} and
	 * {@link GenderType#INCORPORATION}.
	 *
	 * @return the human gender types
	 */
	public static List<GenderType> getHumanGenders()
	{
		final GenderType[] genderValues = GenderType.values();
		final List<GenderType> genders = new ArrayList<>();
		for (int i = 0; i < genderValues.length; i++)
		{
			if (!genderValues[i].equals(GenderType.UNDEFINED)
				&& !genderValues[i].equals(GenderType.INCORPORATION))
			{
				genders.add(genderValues[i]);
			}
		}
		return genders;
	}

	/**
	 * Gets the all genders without the gender type {@link GenderType#UNDEFINED}.
	 *
	 * @return the valid gender types
	 */
	public static List<GenderType> getValidGenders()
	{
		final GenderType[] genderValues = GenderType.values();
		final List<GenderType> genders = new ArrayList<>();
		for (int i = 0; i < genderValues.length; i++)
		{
			if (!genderValues[i].equals(GenderType.UNDEFINED))
			{
				genders.add(genderValues[i]);
			}
		}
		return genders;
	}

}
