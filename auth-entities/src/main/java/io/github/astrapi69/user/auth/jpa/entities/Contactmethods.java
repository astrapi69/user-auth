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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import io.github.astrapi69.entity.uniqueable.UUIDEntity;
import io.github.astrapi69.user.auth.enums.ContactmethodType;
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

	/** The contact method like email, telefon etc. */
	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_NAME_CONTACTMETHOD)
	ContactmethodType contactmethod;

	/**
	 * The value from the contact method. For instance 'abc@gmail.com' for email or
	 * 'http://www.google.com' for internet.
	 */
	@Column(name = COLUMN_NAME_CONTACTVALUE, length = 1024)
	String contactvalue;
}
