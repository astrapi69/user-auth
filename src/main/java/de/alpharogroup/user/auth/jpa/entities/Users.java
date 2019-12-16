/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *  *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *  *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.user.auth.jpa.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.alpharogroup.db.entity.BaseEntity;
import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * The entity class {@link Users} is keeping the information for the users from the application.
 */
@Entity
@Table(name = Users.TABLE_NAME, indexes = { @Index(name = DatabasePrefix.INDEX_PREFIX + Users.TABLE_NAME
	+ DatabasePrefix.UNDERSCORE
	+ Users.COLUMN_NAME_USERNAME, columnList = Users.COLUMN_NAME_USERNAME, unique = true) }, uniqueConstraints = {
			@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX + Users.TABLE_NAME
				+ DatabasePrefix.UNDERSCORE
				+ Users.COLUMN_NAME_USERNAME, columnNames = { Users.COLUMN_NAME_USERNAME }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users extends UUIDEntity implements Cloneable
{

	static final String COLUMN_NAME_USERNAME = "username";
	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;

	static final String TABLE_NAME = "users";
	/** The attribute active, if true the user account is active. */
	@Column
	boolean active;
	/** A Flag that indicates if the user account is locked or not. */
	@Column
	boolean locked;
	/** The hash from the password hashed with sha512. */
	@Column(length = 1024)
	String pw;
	/** The roles of the user. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_roles_user_id")) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_roles_role_id")) })
	Set<Roles> roles = new HashSet<>();
	/** The salt that is used to compute the hash. */
	@Column(length = 8)
	String salt;
	/** The user name. */
	@Column(name = COLUMN_NAME_USERNAME, length = 256)
	String username;

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
