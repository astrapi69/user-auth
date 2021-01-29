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
package de.alpharogroup.user.auth.jpa.entities;

import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest
{

	@Test
	public void testConstants(){
		String actual;
		String expected;
		//
		actual = Users.SINGULAR_ENTITY_NAME;
		expected = "user";
		assertEquals(actual, expected);
		//
		actual = Users.TABLE_NAME;
		expected = "users";
		assertEquals(actual, expected);
		//
		actual = Users.COLUMN_NAME_USERNAME;
		expected = "username";
		assertEquals(actual, expected);
		//
		actual = Users.COLUMN_NAME_EMAIL;
		expected = "email";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_COLUMN_NAME_APPLICATION;
		expected = "application";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_TABLE_NAME_USER_ROLES;
		expected = "user_roles";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_TABLE_USER_ROLES_COLUMN_NAME_USER_ID;
		expected = "user_id";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_TABLE_USER_ROLES_COLUMN_NAME_ROLE_ID;
		expected = "role_id";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_TABLE_FOREIGN_KEY_USER_ROLES_USER_ID;
		expected = "fk_user_roles_user_id";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_TABLE_FOREIGN_KEY_USER_ROLES_ROLE_ID;
		expected = "fk_user_roles_role_id";
		assertEquals(actual, expected);
		//
		actual = Users.JOIN_COLUMN_FOREIGN_KEY_USERS_APPLICATION_ID;
		expected = "fk_users_application_id";
		assertEquals(actual, expected);

	}

}
