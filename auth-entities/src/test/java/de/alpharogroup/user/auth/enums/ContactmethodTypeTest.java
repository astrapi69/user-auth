package de.alpharogroup.user.auth.enums;

import de.alpharogroup.lang.ClassExtensions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactmethodTypeTest
{
	@Test public void testTypeConstant()
	{
		String enumClassNameValue = ContactmethodType.ENUM_CLASS_NAME_VALUE;
		String classname = ClassExtensions.getClassname(ContactmethodType.class);
		assertEquals(classname, enumClassNameValue);
	}
}
