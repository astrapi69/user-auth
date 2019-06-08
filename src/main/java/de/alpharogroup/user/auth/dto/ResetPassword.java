package de.alpharogroup.user.auth.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPassword
{
	/** The date which this data expire. */
	LocalDateTime expiryDate;
	/** mapping */
	String generatedPassword;
	/** The time that the user send the form. */
	LocalDateTime starttime;
	/** The user attribute that references to the domain class {@link User}. */
	User user;
}
