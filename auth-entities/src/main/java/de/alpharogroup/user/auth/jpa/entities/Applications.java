package de.alpharogroup.user.auth.jpa.entities;


import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.nameable.Nameable;
import de.alpharogroup.db.entity.nameable.versionable.VersionableNameUUIDEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = Applications.TABLE_NAME, uniqueConstraints = {
	@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX
		+ Applications.TABLE_NAME + DatabasePrefix.UNDERSCORE
		+ Nameable.COLUMN_NAME_NAME, columnNames = Nameable.COLUMN_NAME_NAME) }, indexes = {
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

	@Column(name = COLUMN_NAME_DOMAIN_NAME, length = 1024)
	String domainName;

	@Column(length = 1024)
	String email;

}
