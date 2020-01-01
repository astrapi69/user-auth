package de.alpharogroup.user.auth.service;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.alpharogroup.servlet.extensions.enums.HeaderKeyNames;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;
import de.alpharogroup.user.auth.enums.ApplicationHeaderKeyNames;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtTokenService
{
	ApplicationProperties applicationProperties;

	public String getUsername(String token)
	{
		return getClaims(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token)
	{
		return getClaims(token, Claims::getExpiration);
	}

	public <T> T getClaims(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaims(String token)
	{
		return Jwts.parser()
			.setSigningKey(applicationProperties.getJwtSecret().getBytes())
			.parseClaimsJws(token.replace(HeaderKeyNames.BEARER_PREFIX, ""))
			.getBody();
	}

	public String newJwtToken(UserDetails userDetails)
	{
		var roles = userDetails.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());

		String jwtSecret = applicationProperties.getJwtSecret();
		var signingKey = jwtSecret.getBytes();

		var token = Jwts.builder()
			.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
			.setHeaderParam(HeaderKeyNames.TOKEN_TYPE_KEY, HeaderKeyNames.DEFAULT_TOKEN_TYPE)
			.setIssuer(ApplicationHeaderKeyNames.DEFAULT_TOKEN_ISSUER)
			.setAudience(ApplicationHeaderKeyNames.DEFAULT_TOKEN_AUDIENCE)
			.setSubject(userDetails.getUsername())
			.setExpiration(new Date(System.currentTimeMillis()
				+ Integer.valueOf(HeaderKeyNames.DEFAULT_DURABILITY))) // in 2 hours
			.claim(ApplicationHeaderKeyNames.HEADER_KEY_ROLES, roles)
			.compact();
		return token;
	}

	public Boolean validate(String token, UserDetails userDetails)
	{
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername())
			&& !getExpirationDate(token).before(new Date()));
	}

}
