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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;

import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The entity class {@link Users} is keeping the information for the users from the application.
 */
@Entity
@Table(name = Users.TABLE_NAME, indexes = { @Index(name = DatabasePrefix.INDEX_PREFIX
	+ Users.TABLE_NAME + DatabasePrefix.UNDERSCORE + Users.JOIN_COLUMN_NAME_APPLICATION
	+ DatabasePrefix.UNDERSCORE
	+ Users.JOIN_COLUMN_NAME_APPLICATION, columnList = Users.JOIN_COLUMN_NAME_APPLICATION + ","
		+ Users.COLUMN_NAME_USERNAME, unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Users.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Users.JOIN_COLUMN_NAME_APPLICATION + DatabasePrefix.UNDERSCORE
					+ Users.COLUMN_NAME_USERNAME, columnNames = {
							Users.JOIN_COLUMN_NAME_APPLICATION, Users.COLUMN_NAME_USERNAME }),
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Users.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Users.COLUMN_NAME_USERNAME, columnNames = { Users.COLUMN_NAME_USERNAME }),
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Users.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Users.COLUMN_NAME_EMAIL, columnNames = { Users.COLUMN_NAME_EMAIL }),
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Users.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Users.JOIN_COLUMN_NAME_APPLICATION + DatabasePrefix.UNDERSCORE
					+ Users.COLUMN_NAME_EMAIL, columnNames = { Users.JOIN_COLUMN_NAME_APPLICATION,
							Users.COLUMN_NAME_EMAIL }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users extends UUIDEntity
{
	static final String SINGULAR_ENTITY_NAME = "user";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_USERNAME = "username";
	static final String COLUMN_NAME_EMAIL = "email";
	static final String JOIN_COLUMN_NAME_APPLICATION = Applications.SINGULAR_ENTITY_NAME;
	static final String JOIN_TABLE_NAME_USER_ROLES = Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Roles.TABLE_NAME;
	static final String JOIN_TABLE_USER_ROLES_COLUMN_NAME_USER_ID = Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_USER_ROLES_COLUMN_NAME_ROLE_ID = Roles.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_ROLES_USER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_ROLES + DatabasePrefix.UNDERSCORE
		+ JOIN_TABLE_USER_ROLES_COLUMN_NAME_USER_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_ROLES_ROLE_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_ROLES + DatabasePrefix.UNDERSCORE
		+ JOIN_TABLE_USER_ROLES_COLUMN_NAME_ROLE_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USERS_APPLICATION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_APPLICATION
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	/** The {@link Applications} that owns this {@link Users} object. */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = JOIN_COLUMN_NAME_APPLICATION, nullable = false, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USERS_APPLICATION_ID))
	Applications applications;

	/** The attribute active, if true the user account is active. */
	@Column
	boolean active;

	/** A Flag that indicates if the user account is locked or not. */
	@Column
	boolean locked;

	/** The hash from the password hashed with sha512. */
	@Column(length = 1024)
	String password;

	/** The roles of the user. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_USER_ROLES, joinColumns = {
			@JoinColumn(name = JOIN_TABLE_USER_ROLES_COLUMN_NAME_USER_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_ROLES_USER_ID)) }, inverseJoinColumns = {
					@JoinColumn(name = JOIN_TABLE_USER_ROLES_COLUMN_NAME_ROLE_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_ROLES_ROLE_ID)) })
	Set<Roles> roles = new HashSet<>();

	/** The salt that is used to compute the hash. */
	@Column(length = 8)
	String salt;

	/** The user name. */
	@Column(name = COLUMN_NAME_USERNAME, length = 256)
	String username;

	/** The email of this user. */
	@Column(name = COLUMN_NAME_EMAIL, length = 512)
	@Email
	String email;

	/**
	 * Adds the given role to this {@link Users} object
	 *
	 * @param role
	 *            the role
	 * @return true, if successful
	 */
	public boolean addRole(Roles role)
	{
		return roles.add(role);
	}

	/**
	 * Removes the given role from this {@link Users} object
	 *
	 * @param role
	 *            the role
	 * @return true, if successful
	 */
	public boolean removeRole(Roles role)
	{
		return roles.remove(role);
	}

}
