package de.alpharogroup.user.auth.enums;

import de.alpharogroup.lang.ClassExtensions;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenderTypeTest
{

	@Test public void testTypeConstant()
	{
		String enumClassNameValue = GenderType.ENUM_CLASS_NAME_VALUE;
		String classname = ClassExtensions.getClassname(GenderType.class);
		assertEquals(classname, enumClassNameValue);
	}
}
