package de.alpharogroup.user.auth.dto;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
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
