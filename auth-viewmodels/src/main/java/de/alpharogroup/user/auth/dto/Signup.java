package de.alpharogroup.user.auth.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Signup
{
	String username;

	String email;

	String password;

	Set<String> roles;
}
