package de.alpharogroup.user.auth.entrypoint;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.alpharogroup.user.auth.enums.HeaderKeyNames;
import de.alpharogroup.user.auth.filter.HttpServletRequestExtensions;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{

	@Override public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException
	{
		Optional<String> authorizationHeader = HttpServletRequestExtensions
			.getAuthorizationHeader(request);
		if (authorizationHeader.isPresent())
		{
			String jwtToken = authorizationHeader.get();
			response.addHeader(HeaderKeyNames.AUTHORIZATION, HeaderKeyNames.BEARER_PREFIX + jwtToken);
		}else
		{
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}
}
