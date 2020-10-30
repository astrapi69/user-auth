package de.alpharogroup.user.auth.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordMessage
{
	ResetPassword resetPassword;
	String applicationDomainName;
	String applicationSenderAddress;
	String usersEmail;
}
