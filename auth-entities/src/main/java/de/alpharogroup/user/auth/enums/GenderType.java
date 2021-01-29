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

	public static final String ENUM_CLASS_NAME_VALUE = "de.alpharogroup.user.auth.enums.GenderType";

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
