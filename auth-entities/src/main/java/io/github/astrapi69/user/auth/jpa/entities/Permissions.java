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
package io.github.astrapi69.user.auth.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.github.astrapi69.data.enumeration.DatabasePrefix;
import io.github.astrapi69.entity.uniqueable.UUIDEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The entity class {@link Permissions} is keeping the information for the permissions of a role or
 * roles.
 */
@Entity
@Table(name = Permissions.TABLE_NAME, indexes = { @Index(name = DatabasePrefix.INDEX_PREFIX
	+ Permissions.TABLE_NAME + DatabasePrefix.UNDERSCORE + Permissions.COLUMN_NAME_NAME
	+ DatabasePrefix.UNDERSCORE
	+ Permissions.COLUMN_NAME_SHORTCUT, columnList = Permissions.COLUMN_NAME_NAME + ","
		+ Permissions.COLUMN_NAME_SHORTCUT, unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Permissions.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Permissions.COLUMN_NAME_NAME, columnNames = { Permissions.COLUMN_NAME_NAME }),
				@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PG_PREFIX
					+ Permissions.TABLE_NAME + DatabasePrefix.UNDERSCORE
					+ Permissions.COLUMN_NAME_SHORTCUT, columnNames = {
							Permissions.COLUMN_NAME_SHORTCUT }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permissions extends UUIDEntity
{
	static final String SINGULAR_ENTITY_NAME = "permission";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_NAME = "name";
	static final String COLUMN_NAME_SHORTCUT = "shortcut";
	static final String COLUMN_NAME_DESCRIPTION = "description";

	/** A description for the permission. */
	@Column(name = COLUMN_NAME_DESCRIPTION, length = 64)
	String description;
	/** The name from the permission. */
	@Column(name = COLUMN_NAME_NAME, length = 64)
	String name;
	/** A shortcut for the permission. */
	@Column(name = COLUMN_NAME_SHORTCUT, length = 10)
	String shortcut;
}
