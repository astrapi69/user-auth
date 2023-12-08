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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import io.github.astrapi69.data.enumeration.DatabasePrefix;
import io.github.astrapi69.data.identifiable.Identifiable;
import io.github.astrapi69.entity.uniqueable.UUIDEntity;
import io.github.astrapi69.user.auth.enums.GenderType;
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
 * The entity class {@link UserInfos} UserInfos hold user specific information
 */
@Entity
@Table(name = UserInfos.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfos extends UUIDEntity
{
	static final String SINGULAR_ENTITY_NAME = "user_info";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME + "s";
	static final String COLUMN_NAME_OWNER = "owner";
	static final String COLUMN_NAME_IP_ADDRESS = "ip_address";
	static final String COLUMN_NAME_STRIPE_CUSTOMER_ID = "stripe_customer_id";
	static final String CONVERTER_NAME_GENDER = "genderConverter";
	static final String JOIN_COLUMN_NAME_USER_INFOS_ID = TABLE_NAME + DatabasePrefix.UNDERSCORE
		+ Identifiable.COLUMN_NAME_ID;
	static final String JOIN_COLUMN_NAME_CONTACTMETHODS_ID = Contactmethods.TABLE_NAME
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_NAME_USER_CONTACTMETHODS = Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Contactmethods.TABLE_NAME;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_INFOS_USER_INFOS_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_USER_INFOS_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_INFOS_CONTACTMETHODS_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_CONTACTMETHODS_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_INFOS_USER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX
		+ TABLE_NAME + DatabasePrefix.UNDERSCORE + Users.SINGULAR_ENTITY_NAME
		+ DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;

	/** The owner of this user data. */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = COLUMN_NAME_OWNER, foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_INFOS_USER_ID))
	Users owner;
	/** The birth name from the user if he or she had one. */
	@Column(length = 64)
	String birthname;
	/** The contact data of the user. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JoinTable(name = JOIN_TABLE_NAME_USER_CONTACTMETHODS, joinColumns = {
			@JoinColumn(name = JOIN_COLUMN_NAME_USER_INFOS_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_INFOS_USER_INFOS_ID)) }, inverseJoinColumns = {
					@JoinColumn(name = JOIN_COLUMN_NAME_CONTACTMETHODS_ID, referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY, foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_INFOS_CONTACTMETHODS_ID)) })
	Set<Contactmethods> contactmethods = new HashSet<>();
	/** The date of birth from the user. */
	Date dateofbirth;
	/** The first name of the user. */
	@Column(length = 64)
	String firstname;
	/** The enum for the gender of the user. */
	@Enumerated(EnumType.STRING)
	@Column
	GenderType gender;
	/** The ip address from where the user has register his self. */
	@Column(name = COLUMN_NAME_IP_ADDRESS, length = 16)
	String ipAddress;
	/** The last name of the user. */
	@Column(length = 64)
	String lastname;
	/** The locale from the user when she/he registered. */
	@Column(length = 12)
	String locale;
	/** The unit points that the user have bought */
	@Column(nullable = true)
	Long credits;
	/** The customer id from stripe */
	@Column(name = COLUMN_NAME_STRIPE_CUSTOMER_ID, length = 64)
	String stripeCustomerId;

}
