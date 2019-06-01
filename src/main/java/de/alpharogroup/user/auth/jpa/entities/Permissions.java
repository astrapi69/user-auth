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

import javax.persistence.*;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * The entity class {@link Permissions} is keeping the information for the permissions of a role or
 * roles.
 */
@Entity
@Table(name = Permissions.TABLE_NAME, indexes = { @Index(name = BaseEntity.INDEX_PREFIX + Permissions.TABLE_NAME
		+ BaseEntity.UNDERSCORE
		+ Permissions.COLUMN_NAME_NAME + BaseEntity.UNDERSCORE + Permissions.COLUMN_NAME_SHORTCUT, columnList = Permissions.COLUMN_NAME_NAME + "," + Permissions.COLUMN_NAME_SHORTCUT, unique = true) },
		uniqueConstraints = {
		@UniqueConstraint(name = BaseEntity.UNIQUE_CONSTRAINT_PREFIX + Permissions.TABLE_NAME
			+ BaseEntity.UNDERSCORE
			+ Permissions.COLUMN_NAME_NAME, columnNames = { Permissions.COLUMN_NAME_NAME }),
		@UniqueConstraint(name = BaseEntity.UNIQUE_CONSTRAINT_PREFIX + Permissions.TABLE_NAME
			+ BaseEntity.UNDERSCORE + Permissions.COLUMN_NAME_SHORTCUT, columnNames = {
					Permissions.COLUMN_NAME_SHORTCUT }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permissions extends BaseEntity<Integer> implements Cloneable
{

	static final String TABLE_NAME = "permissions";
	static final String COLUMN_NAME_NAME = "name";
	static final String COLUMN_NAME_SHORTCUT = "shortcut";

	/** The serial Version UID */
	private static final long serialVersionUID = 1L;
	/** A description for the permission. */
	@Column(name = "description", length = 64)
	String description;
	/** The name from the permission. */
	@Column(name = COLUMN_NAME_NAME, length = 64)
	String name;
	/** A shortcut for the permission. */
	@Column(name = COLUMN_NAME_SHORTCUT, length = 10)
	String shortcut;
}
