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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.alpharogroup.db.entity.BaseEntity;
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
 * The entity class {@link UserTokens} is keeping the information for the token of users.
 */
@Entity
@Table(name = UserTokens.TABLE_NAME, uniqueConstraints = {
		@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX + UserTokens.TABLE_NAME
			+ DatabasePrefix.UNDERSCORE
			+ UserTokens.COLUMN_NAME_TOKEN, columnNames = { UserTokens.COLUMN_NAME_TOKEN }),
		@UniqueConstraint(name = DatabasePrefix.UNIQUE_CONSTRAINT_PREFIX + UserTokens.TABLE_NAME
			+ DatabasePrefix.UNDERSCORE
			+ UserTokens.COLUMN_NAME_USERNAME, columnNames = { UserTokens.COLUMN_NAME_USERNAME }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTokens extends UUIDEntity implements Cloneable
{

	static final String COLUMN_NAME_TOKEN = "token";
	static final String COLUMN_NAME_USERNAME = "username";
	/** The serial Version UID */
	private static final long serialVersionUID = 1L;
	static final String TABLE_NAME = "user_tokens";
	/** The expiration date. */
	@Column
	LocalDateTime expiry;
	/** The token for the user. */
	@Column(name = COLUMN_NAME_TOKEN, length = 128)
	String token;
	/** The user name. */
	@Column(name = COLUMN_NAME_USERNAME, length = 256)
	String username;
}
