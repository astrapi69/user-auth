package de.alpharogroup.user.auth.jpa.entities;

import de.alpharogroup.db.DatabaseDefaults;
import de.alpharogroup.db.entity.enums.DatabasePrefix;
import de.alpharogroup.db.entity.identifiable.Identifiable;
import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import de.alpharogroup.user.auth.enums.GenderType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity class {@link UserInfos} UserInfos hold user specific information
 */
@Entity
@Table(name = UserInfos.TABLE_NAME)
@TypeDefs({
	@TypeDef(name = UserInfos.CONVERTER_NAME_GENDER,
		typeClass = de.alpharogroup.db.postgres.usertype.PGEnumUserType.class, parameters = {
		@Parameter(name = DatabaseDefaults.ENUM_CLASS_NAME,
			value = GenderType.ENUM_CLASS_NAME_VALUE) }) })
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
	static final String JOIN_COLUMN_NAME_USER_INFOS_ID = TABLE_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_COLUMN_NAME_CONTACTMETHODS_ID = Contactmethods.TABLE_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;
	static final String JOIN_TABLE_NAME_USER_CONTACTMETHODS = "user_" + Contactmethods.TABLE_NAME;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_INFOS_USER_INFOS_ID = DatabasePrefix.FOREIGN_KEY_PREFIX +
		TABLE_NAME + DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_USER_INFOS_ID;
	static final String JOIN_TABLE_FOREIGN_KEY_USER_INFOS_CONTACTMETHODS_ID = DatabasePrefix.FOREIGN_KEY_PREFIX +
		TABLE_NAME + DatabasePrefix.UNDERSCORE + JOIN_COLUMN_NAME_CONTACTMETHODS_ID;
	static final String JOIN_COLUMN_FOREIGN_KEY_USER_INFOS_USER_ID = DatabasePrefix.FOREIGN_KEY_PREFIX +
		TABLE_NAME + DatabasePrefix.UNDERSCORE + Users.TABLE_NAME + DatabasePrefix.UNDERSCORE + Identifiable.COLUMN_NAME_ID;

	/** The owner of this user data. */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = COLUMN_NAME_OWNER,
		foreignKey = @ForeignKey(name = JOIN_COLUMN_FOREIGN_KEY_USER_INFOS_USER_ID))
	private Users owner;
	/** The birth name from the user if he or she had one. */
	@Column(length = 64)
	private String birthname;
	/** The contact data of the user. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JoinTable(name = JOIN_TABLE_NAME_USER_CONTACTMETHODS, joinColumns = {
			@JoinColumn(name = JOIN_COLUMN_NAME_USER_INFOS_ID,
				referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
				foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_INFOS_USER_INFOS_ID)) },
		inverseJoinColumns = {
					@JoinColumn(name = JOIN_COLUMN_NAME_CONTACTMETHODS_ID,
						referencedColumnName = DatabasePrefix.DEFAULT_COLUMN_NAME_PRIMARY_KEY,
						foreignKey = @ForeignKey(name = JOIN_TABLE_FOREIGN_KEY_USER_INFOS_CONTACTMETHODS_ID)) })
	private Set<Contactmethods> contactmethods = new HashSet<>();
	/** The date of birth from the user. */
	private Date dateofbirth;
	/** The first name of the user. */
	@Column(length = 64)
	private String firstname;
	/** The enum for the gender of the user. */
	@Enumerated(EnumType.STRING)
	@Column
	@Type(type = CONVERTER_NAME_GENDER)
	private GenderType gender;
	/** The ip address from where the user has register his self. */
	@Column(name = COLUMN_NAME_IP_ADDRESS, length = 16)
	private String ipAddress;
	/** The last name of the user. */
	@Column(length = 64)
	private String lastname;
	/** The locale from the user when she/he registered. */
	@Column(length = 12)
	private String locale;
	/** The unit points that the user have bought */
	@Column(nullable = true)
	Long credits;
	/** The customer id from stripe */
	@Column(name = COLUMN_NAME_STRIPE_CUSTOMER_ID, length = 64)
	private String stripeCustomerId;

}
