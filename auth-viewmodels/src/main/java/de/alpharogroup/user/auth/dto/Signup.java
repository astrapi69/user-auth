package de.alpharogroup.user.auth.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Signup
{
	String username;

	String email;

	String password;

	Set<String> roles;
}
