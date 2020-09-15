package de.alpharogroup.user.auth.jpa.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.alpharogroup.user.auth.enums.GenderType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
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
@TypeDefs({
	@TypeDef(name = "genderConverter", typeClass = de.alpharogroup.db.postgres.usertype.PGEnumUserType.class, parameters = {
		@org.hibernate.annotations.Parameter(name = "enumClassName", value = "de.alpharogroup.user.auth.enums.GenderType") }) })
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfos extends UUIDEntity
{

	static final String TABLE_NAME = "user_infos";
	/** The owner of this user data. */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner", foreignKey = @ForeignKey(name = "fk_user_infos_users_id"))
	private Users owner;
	/** The birth name from the user if he or she had one. */
	@Column(name = "birthname", length = 64)
	private String birthname;
	/** The contact data of the user. */
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@JoinTable(name = "user_contactmethods", joinColumns = {
			@JoinColumn(name = "user_infos_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_infos_user_infos_id")) }, inverseJoinColumns = {
					@JoinColumn(name = "contactmethods_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_infos_contactmethods_id")) })
	private Set<Contactmethods> contactmethods = new HashSet<>();
	/** The date of birth from the user. */
	private Date dateofbirth;
	/** The first name of the user. */
	@Column(name = "firstname", length = 64)
	private String firstname;
	/** The enum for the gender of the user. */
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	@Type(type = "genderConverter")
	private GenderType gender;
	/** The ip address from where the user has register his self. */
	@Column(name = "ip_address", length = 16)
	private String ipAddress;
	/** The last name of the user. */
	@Column(name = "lastname", length = 64)
	private String lastname;
	/** The locale from the user when she/he registered. */
	@Column(name = "locale", length = 12)
	private String locale;
	/** The unit points that the user have bought */
	@Column(name = "credits", nullable = true)
	Long credits;
	/** The customer id from stripe */
	@Column(name = "stripe_customer_id", length = 64)
	private String stripeCustomerId;

}
