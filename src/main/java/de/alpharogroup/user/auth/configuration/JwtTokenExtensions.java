package de.alpharogroup.user.auth.configuration;

import de.alpharogroup.user.auth.enums.HeaderKeyNames;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenExtensions
{
	@Autowired
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
			.setIssuer(HeaderKeyNames.DEFAULT_TOKEN_ISSUER)
			.setAudience(HeaderKeyNames.DEFAULT_TOKEN_AUDIENCE)
			.setSubject(userDetails.getUsername())
			.setExpiration(new Date(System.currentTimeMillis()
				+ Integer.valueOf(HeaderKeyNames.DEFAULT_DURABILITY))) // in 2 hours
			.claim(HeaderKeyNames.HEADER_KEY_ROLES, roles)
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
