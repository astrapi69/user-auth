package de.alpharogroup.user.auth.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.alpharogroup.db.DatabaseDefaults;
import de.alpharogroup.user.auth.enums.ContactmethodType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The entity class {@link Contactmethods} is keeping the information for the contact methods and
 * the corresponding value.
 */
@Entity
@Table(name = Contactmethods.TABLE_NAME)
@TypeDef(name = Contactmethods.CONVERTER_NAME_CONTACTMETHOD, typeClass = de.alpharogroup.db.postgres.usertype.PGEnumUserType.class, parameters = {
		@Parameter(name = DatabaseDefaults.ENUM_CLASS_NAME, value = ContactmethodType.ENUM_CLASS_NAME_VALUE) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contactmethods extends UUIDEntity
{

	static final String SINGULAR_ENTITY_NAME = "contactmethod";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_CONTACTMETHOD = "contactmethod";
	static final String COLUMN_NAME_CONTACTVALUE = "contactvalue";
	static final String CONVERTER_NAME_CONTACTMETHOD = "contactmethodConverter";
	public static final String ENUM_CLASS_NAME = "enumClassName";

	/** The contact method like email, telefon etc. */
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_NAME_CONTACTMETHOD)
	@Type(type = Contactmethods.CONVERTER_NAME_CONTACTMETHOD)
	ContactmethodType contactmethod;

	/**
	 * The value from the contact method. For instance 'abc@gmail.com' for email or
	 * 'http://www.google.com' for internet.
	 */
	@Column(name = COLUMN_NAME_CONTACTVALUE, length = 1024)
	String contactvalue;
}
