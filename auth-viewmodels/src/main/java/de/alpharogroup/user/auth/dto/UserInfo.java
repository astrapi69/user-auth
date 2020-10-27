package de.alpharogroup.user.auth.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserInfo
{
	User owner;
	String birthname;
	Set<Contactmethod> contactmethods = new HashSet<>();
	Date dateofbirth;
	String firstname;
	String gender;
	/** The ip address from where the user has register his self. */
	String ipAddress;
	/** The last name of the user. */
	String lastname;
	/** The locale from the user when she/he registered. */
	String locale;
	/** The unit points that the user have bought */
	Long credits;
	/** The customer id from stripe */
	String stripeCustomerId;
}
