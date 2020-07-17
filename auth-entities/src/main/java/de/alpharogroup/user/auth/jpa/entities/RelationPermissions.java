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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
 * This class describes the permissions that a user can give to another user. For instance: if a
 * user(the provider of the permissions) have private resources like images and want to release them
 * to another user(the subscriber) so he can see this resources, than an entry of a provider and the
 * specified permission have to be added in the set of permission.
 */
@Entity
@Table(name = RelationPermissions.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RelationPermissions extends UUIDEntity implements Cloneable
{

	/** The serial Version UID */
	private static final long serialVersionUID = 1L;
	static final String SINGULAR_ENTITY_NAME = "relation_permission";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME+"s";
	static final String JOIN_COLUMN_NAME_PROVIDER_ID = "provider_id";
	static final String JOIN_COLUMN_NAME_SUBSCRIBER_ID = "subscriber_id";
	static final String JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS = "user_" + TABLE_NAME;
	static final String JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID = "user_relation_permission_id";
	static final String JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID = "permission_id";
	static final String JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_USER_RELATION_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX +
		JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE +
		JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX +
		JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE +
		JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PROVIDER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX  +
		JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE +
		JOIN_COLUMN_NAME_PROVIDER_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_SUBSCRIBER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX  +
		JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE +
		JOIN_COLUMN_NAME_SUBSCRIBER_ID;

	/** The permissions of the role. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS, joinColumns = {
			@JoinColumn(name = JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID,
				referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
				foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_USER_RELATION_PERMISSION_ID)) },
		inverseJoinColumns = {
					@JoinColumn(name = JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID,
						referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
						foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PERMISSION_ID)) })
	Set<Permissions> permissions = new HashSet<>();
	/** The provider of the permissions. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = JOIN_COLUMN_NAME_PROVIDER_ID, nullable = true,
		referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
		foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PROVIDER_ID))
	Users provider;
	/** The subscriber of the permissions. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = JOIN_COLUMN_NAME_SUBSCRIBER_ID, nullable = true,
		referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
		foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_SUBSCRIBER_ID))
	Users subscriber;
}