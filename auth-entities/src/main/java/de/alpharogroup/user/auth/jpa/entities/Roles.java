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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
import lombok.experimental.SuperBuilder;

/**
 * The entity class {@link Roles} is keeping the information for the user roles.
 */
@Entity
@Table(name = Roles.TABLE_NAME, uniqueConstraints = {
		@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX + Roles.TABLE_NAME
			+ DatabasePrefix.UNDERSCORE
			+ Roles.COLUMN_NAME_NAME, columnNames = { Roles.COLUMN_NAME_NAME }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles extends UUIDEntity implements Cloneable
{
	static final String COLUMN_NAME_NAME = "name";
	static final String COLUMN_NAME_DESCRIPTION = "description";
	static final String JOIN_TABLE_NAME_ROLE_PERMISSIONS = "role_permissions";
	static final String JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_ROLE_ID = "role_id";
	static final String JOIN_TABLE_ROLE_PERMISSIONS_USER_REFERENCED_COLUMN_NAME = "id";
	static final String JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_PERMISSION_ID = "permission_id";
	static final String JOIN_TABLE_ROLE_PERMISSIONS_ROLES_REFERENCED_COLUMN_NAME = "id";
	static final String JOIN_TABLE_FOREIGN_KEY_ROLE_PERMISSIONS_ROLE_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_ROLE_PERMISSIONS + DatabasePrefix.UNDERSCORE + JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_ROLE_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_ROLE_PERMISSIONS_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_ROLE_PERMISSIONS + DatabasePrefix.UNDERSCORE + JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_PERMISSION_ID;

	/** The serial Version UID. */
	private static final long serialVersionUID = 1L;
	static final String TABLE_NAME = "roles";
	/** A description of the role. */
	@Column(name = COLUMN_NAME_DESCRIPTION, length = 64)
	String description;
	/** The name of the role. */
	@Column(name = COLUMN_NAME_NAME, length = 64)
	String name;
	/** The permissions of the role. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_ROLE_PERMISSIONS, joinColumns = {
			@JoinColumn(name = JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_ROLE_ID,
				referencedColumnName = JOIN_TABLE_ROLE_PERMISSIONS_USER_REFERENCED_COLUMN_NAME,
				foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_ROLE_PERMISSIONS_ROLE_ID)) }, inverseJoinColumns = {
					@JoinColumn(name = JOIN_TABLE_ROLE_PERMISSIONS_COLUMN_NAME_PERMISSION_ID,
						referencedColumnName = JOIN_TABLE_ROLE_PERMISSIONS_ROLES_REFERENCED_COLUMN_NAME,
						foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_ROLE_PERMISSIONS_PERMISSION_ID)) })
	Set<Permissions> permissions = new HashSet<>();

	/**
	 * Adds the given permission to this {@link Roles} object
	 *
	 * @param permission
	 *            the permission to add
	 * @return true, if successful
	 */
	public boolean addPermission(Permissions permission)
	{
		return permissions.add(permission);
	}

	/**
	 * Removes the given permission from this {@link Roles} object
	 *
	 * @param permission
	 *            the permission to remove
	 * @return true, if successful
	 */
	public boolean removePermission(Permissions permission)
	{
		return permissions.remove(permission);
	}

}
