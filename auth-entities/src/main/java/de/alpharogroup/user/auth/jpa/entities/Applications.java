package de.alpharogroup.user.auth.jpa.entities;


import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import de.alpharogroup.db.entity.nameable.Nameable;
import de.alpharogroup.db.entity.nameable.versionable.VersionableNameUUIDEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Applications.TABLE_NAME, uniqueConstraints = {
	@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX
		+ Applications.TABLE_NAME + DatabasePrefix.UNDERSCORE
		+ Nameable.COLUMN_NAME_NAME, columnNames = Nameable.COLUMN_NAME_NAME),
	@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX
		+ Applications.TABLE_NAME + DatabasePrefix.UNDERSCORE
		+ Applications.COLUMN_NAME_DOMAIN_NAME, columnNames = Applications.COLUMN_NAME_DOMAIN_NAME),
	@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX
		+ Applications.TABLE_NAME + DatabasePrefix.UNDERSCORE
		+ Applications.COLUMN_NAME_EMAIL, columnNames = Applications.COLUMN_NAME_EMAIL)},
	indexes = {
	@Index(name = DatabasePrefix.INDEX_PREFIX + Applications.TABLE_NAME
		+ DatabasePrefix.UNDERSCORE
		+ Nameable.COLUMN_NAME_NAME, columnList = Nameable.COLUMN_NAME_NAME, unique = true) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class Applications extends VersionableNameUUIDEntity
{
	static final String SINGULAR_ENTITY_NAME = "application";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_DOMAIN_NAME = "domain_name";
	static final String COLUMN_NAME_EMAIL = "email";

	static final String JOIN_TABLE_NAME_APPLICATION_ROLES = Applications.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Roles.TABLE_NAME;
	static final String JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_APPLICATION_ID = Applications.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_ROLE_ID = Roles.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_APPLICATION_ROLES_APPLICATION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_APPLICATION_ROLES + DatabasePrefix.UNDERSCORE + JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_APPLICATION_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_APPLICATION_ROLES_ROLE_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_APPLICATION_ROLES + DatabasePrefix.UNDERSCORE + JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_ROLE_ID;

	static final String JOIN_TABLE_NAME_APPLICATION_PERMISSIONS = Applications.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Permissions.TABLE_NAME;
	static final String JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_APPLICATION_ID = Applications.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_PERMISSION_ID = Permissions.SINGULAR_ENTITY_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_APPLICATION_PERMISSIONS_APPLICATION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_APPLICATION_PERMISSIONS + DatabasePrefix.UNDERSCORE + JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_APPLICATION_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_APPLICATION_PERMISSIONS_PERMISSION_ID = DatabasePrefix.FOREIGN_KEY_PREFIX + JOIN_TABLE_NAME_APPLICATION_PERMISSIONS + DatabasePrefix.UNDERSCORE + JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_PERMISSION_ID;

	/** The domain name of this application. */
	@Column(name = COLUMN_NAME_DOMAIN_NAME, length = 1024)
	String domainName;

	/** The info email of this application. */
	@Column(length = 1024)
	String email;

	/** The allowed roles of the application. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_APPLICATION_ROLES, joinColumns = {
		@JoinColumn(name = JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_APPLICATION_ID,
			referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
			foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_APPLICATION_ROLES_APPLICATION_ID)) },
		inverseJoinColumns = {
			@JoinColumn(name = JOIN_TABLE_APPLICATION_ROLES_COLUMN_NAME_ROLE_ID,
				referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
				foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_APPLICATION_ROLES_ROLE_ID)) }) Set<Roles> roles = new HashSet<>();

	/** The allowed permissions of the application. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = JOIN_TABLE_NAME_APPLICATION_PERMISSIONS, joinColumns = {
		@JoinColumn(name = JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_APPLICATION_ID,
			referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
			foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_APPLICATION_PERMISSIONS_APPLICATION_ID)) }, inverseJoinColumns = {
		@JoinColumn(name = JOIN_TABLE_APPLICATION_PERMISSIONS_COLUMN_NAME_PERMISSION_ID,
			referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
			foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_APPLICATION_PERMISSIONS_PERMISSION_ID)) })
	Set<Permissions> permissions = new HashSet<>();

}
