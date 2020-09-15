package de.alpharogroup.user.auth.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse
{
	String token;
	String type;
	String username;
	Set<String> roles;
}
