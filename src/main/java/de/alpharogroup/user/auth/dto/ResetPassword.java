package de.alpharogroup.user.auth.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPassword
{
	/** The date which this data expire. */
	private LocalDateTime expiryDate;
	/** mapping */
	private String generatedPassword;
	/** The time that the user send the form. */
	private LocalDateTime starttime;
	/** The user attribute that references to the domain class {@link User}. */
	private User user;
}
