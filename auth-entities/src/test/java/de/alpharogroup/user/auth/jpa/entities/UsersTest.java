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
