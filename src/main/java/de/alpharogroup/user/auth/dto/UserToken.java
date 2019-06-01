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
public class UserToken
{
	/** The user name. */
	private String username;
	/** The token for the user. */
	private String token;
	/** The expiration date. */
	private LocalDateTime expiry;
}
