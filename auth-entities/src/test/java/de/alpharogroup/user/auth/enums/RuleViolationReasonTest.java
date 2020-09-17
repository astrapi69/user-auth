package de.alpharogroup.user.auth.enums;

import de.alpharogroup.lang.ClassExtensions;
import org.junit.Test;

import static org.junit.Assert.*;

public class RuleViolationReasonTest
{

	@Test public void testTypeConstant()
	{
		String enumClassNameValue = RuleViolationReason.ENUM_CLASS_NAME_VALUE;
		String classname = ClassExtensions.getClassname(RuleViolationReason.class);
		assertEquals(classname, enumClassNameValue);
	}
}
