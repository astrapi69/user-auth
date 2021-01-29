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

/**
 * The enum {@link ContactmethodType} defines a view of the types for contact methods that are used
 * for contact.
 */
public enum ContactmethodType
{

	/** The constant for the contact method email. */
	EMAIL,
	/** The constant for the contact method mail. */
	MAIL,
	/** The constant for the contact method telefon. */
	TELEFON,
	/** The constant for the contact method fax. */
	FAX,
	/** The constant for the contact method mobile. */
	MOBILE,
	/** The constant for the contact method sms. */
	SMS,
	/** The constant for the contact method messenger. */
	MESSENGER,
	/** The constant for the contact method internet. */
	INTERNET,
	/** The constant for the contact method newsgroup. */
	NEWSGROUP;

	public static final String ENUM_CLASS_NAME_VALUE = "de.alpharogroup.user.auth.enums.ContactmethodType";

}
