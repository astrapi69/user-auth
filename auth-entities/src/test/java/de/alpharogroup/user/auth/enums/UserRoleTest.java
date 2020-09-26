package de.alpharogroup.user.auth.enums;

import de.alpharogroup.lang.ClassExtensions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRoleTest
{

	@Test public void testTypeConstant()
	{
		String enumClassNameValue = UserRole.ENUM_CLASS_NAME_VALUE;
		String classname = ClassExtensions.getClassname(UserRole.class);
		assertEquals(classname, enumClassNameValue);
	}
}
