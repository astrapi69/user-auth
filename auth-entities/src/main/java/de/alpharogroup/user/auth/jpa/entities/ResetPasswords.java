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

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
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
 * The entity class {@link ResetPasswords} is keeping the information for reseting the password from
 * a user. Data will be inserted occurred a user forgets his password and enter his data in the
 * form.
 */
@Entity
@Table(name = ResetPasswords.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswords extends UUIDEntity implements Cloneable
{
	/** The serial Version UID */
	private static final long serialVersionUID = 1L;

	static final String SINGULAR_ENTITY_NAME = "reset_password";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME+"s";
	static final String COLUMN_NAME_EXPIRY_DATE = "expiry_date";
	static final String COLUMN_NAME_GENERATED_PASSWORD = "generated_password";
	static final String COLUMN_NAME_START_TIME = "starttime";

	static final String JOIN_TABLE_NAME_USER_ROLES = "user_roles";
	static final String JOIN_COLUMN_NAME_USER_ID = "user_id";
	static final String JOIN_COLUMN_FOREIGN_KEY_RESET_PASSWORDS_USER_ID =
		DatabasePrefix.FOREIGN_KEY_PREFIX + TABLE_NAME +
			DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_USER_ID;

	/** The date which this data expire */
	@Column(name = COLUMN_NAME_EXPIRY_DATE)
	LocalDateTime expiryDate;
	/** mapping */
	@Column(name = COLUMN_NAME_GENERATED_PASSWORD, length = 1024)
	String generatedPassword;
	/** The time that the user send the form */
	@Column(name = COLUMN_NAME_START_TIME)
	LocalDateTime starttime;
	/** The user attribute that references to the Entity class {@link Users} */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = JOIN_COLUMN_NAME_USER_ID,
		referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
		foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_RESET_PASSWORDS_USER_ID))
	Users user;
}
