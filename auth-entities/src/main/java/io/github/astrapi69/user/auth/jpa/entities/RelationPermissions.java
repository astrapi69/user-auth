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

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.github.astrapi69.data.enumeration.DatabasePrefix;
import io.github.astrapi69.data.identifiable.Identifiable;
import io.github.astrapi69.entity.uniqueable.UUIDEntity;
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
 * This class describes the permissions that a user(provider) can grant to another user(subscriber).
 * For instance: if a user(the provider of the permissions) have private resources like images and
 * want to release them to another user(the subscriber) so he can see this resources, than an entry
 * of a provider and the specified permission have to be added in the set of permission.
 */
@Entity
@Table(name = RelationPermissions.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RelationPermissions extends UUIDEntity
{
	static final String SINGULAR_ENTITY_NAME = "relation_permission";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_PROVIDER_PREFIX = "provider";
	static final String COLUMN_NAME_SUBSCRIBER_PREFIX = "subscriber";
	static final String JOIN_COLUMN_NAME_PROVIDER_ID = COLUMN_NAME_PROVIDER_PREFIX
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_COLUMN_NAME_SUBSCRIBER_ID = COLUMN_NAME_SUBSCRIBER_PREFIX
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS = Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + TABLE_NAME;
	static final String JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID = Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + RelationPermissions.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID = Permissions.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_USER_RELATION_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE
		+ JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE
		+ JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PROVIDER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE
		+ JOIN_COLUMN_NAME_PROVIDER_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_SUBSCRIBER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS + DatabasePrefix.UNDERSCORE
		+ JOIN_COLUMN_NAME_SUBSCRIBER_ID;

	/** The permissions that are granted to this subscriber */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_USER_RELATION_PERMISSIONS, joinColumns = {
			@JoinColumn(name = JOIN_TABLE_USER_RELATION_COLUMN_NAME_USER_RELATION_PERMISSION_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_USER_RELATION_PERMISSION_ID)) }, inverseJoinColumns = {
					@JoinColumn(name = JOIN_TABLE_USER_RELATION_COLUMN_NAME_PERMISSION_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PERMISSION_ID)) })
	Set<Permissions> permissions = new HashSet<>();

	/** The provider that granted the permissions to this subscriber */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = JOIN_COLUMN_NAME_PROVIDER_ID, nullable = false, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_PROVIDER_ID))
	Users provider;

	/** The subscriber of the permissions */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = JOIN_COLUMN_NAME_SUBSCRIBER_ID, nullable = false, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_RELATION_PERMISSIONS_SUBSCRIBER_ID))
	Users subscriber;
}
