/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.user.auth.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.astrapi69.auth.beans.AuthenticationResult;
import io.github.astrapi69.auth.enumtype.AuthenticationErrors;
import io.github.astrapi69.collection.map.MapFactory;
import io.github.astrapi69.collections.pairs.KeyValuePair;
import io.github.astrapi69.json.JsonStringToObjectExtensions;
import io.github.astrapi69.json.factory.ObjectMapperFactory;
import io.github.astrapi69.servlet.extensions.HttpServletRequestExtensions;
import io.github.astrapi69.servlet.extensions.enums.HeaderKeyNames;
import io.github.astrapi69.user.auth.configuration.ApplicationProperties;
import io.github.astrapi69.user.auth.dto.JwtRequest;
import io.github.astrapi69.user.auth.jpa.entities.Users;
import io.github.astrapi69.user.auth.principal.UsersPrincipal;
import io.github.astrapi69.user.auth.service.JwtTokenService;
import io.github.astrapi69.user.auth.service.api.AuthenticationsService;
import io.github.astrapi69.user.auth.service.jwt.JwtUserDetailsService;
import lombok.NonNull;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
	@Autowired
	ApplicationProperties applicationProperties;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtTokenService jwtTokenService;
	@Autowired
	private AuthenticationsService authenticationsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain) throws ServletException, IOException
	{
		if (isPublicRequest(request)
		// || !request.isSecure()
		)
		{
			chain.doFilter(request, response);
			return;
		}
		String username;
		String password;
		String jwtToken;
		Optional<String> optionalToken = getJwtToken(request);
		if (optionalToken.isPresent())
		{
			jwtToken = optionalToken.get();
			validateToken(request, response, jwtToken);
		}
		else
		{
			String payloadRequest = HttpServletRequestExtensions.getBody(request);
			if (payloadRequest.isEmpty())
			{
				username = request.getParameter("username");
				password = request.getParameter("password");
				if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
				{
					chain.doFilter(request, response);
					return;
				}
			}
			else
			{
				ObjectMapper mapper = ObjectMapperFactory.newObjectMapper(
					MapFactory.newHashMap(KeyValuePair.<JsonParser.Feature, Boolean> builder()
						.key(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES).value(true).build()));
				JwtRequest jwtRequest = JsonStringToObjectExtensions.toObject(payloadRequest,
					JwtRequest.class, mapper);
				username = jwtRequest.getUsername();
				password = jwtRequest.getPassword();
			}
			setNewToken(request, response, username, password);
		}
		chain.doFilter(request, response);
	}

	private void validateToken(HttpServletRequest request, HttpServletResponse response,
		String jwtToken)
	{
		String username = jwtTokenService.getUsername(jwtToken);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UsersPrincipal userDetails = (UsersPrincipal)this.jwtUserDetailsService
				.loadUserByUsername(username);
			if (jwtTokenService.validate(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext()
					.setAuthentication(usernamePasswordAuthenticationToken);
				response.addHeader(HeaderKeyNames.AUTHORIZATION,
					HeaderKeyNames.BEARER_PREFIX + jwtToken);
			}
		}
	}

	private void setNewToken(HttpServletRequest request, HttpServletResponse response,
		String username, String password)
	{
		String jwtToken;
		UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
		AuthenticationResult<Users, AuthenticationErrors> authenticationResult = authenticationsService
			.authenticate(username, password);
		if (authenticationResult.isValid())
		{
			jwtToken = jwtTokenService.newJwtToken(userDetails);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext()
				.setAuthentication(usernamePasswordAuthenticationToken);
			response.addHeader(HeaderKeyNames.AUTHORIZATION,
				HeaderKeyNames.BEARER_PREFIX + jwtToken);
		}
	}

	protected Optional<String> getJwtToken(@NonNull final HttpServletRequest request)
	{
		Optional<String> authorizationHeader = HttpServletRequestExtensions
			.getAuthorizationHeader(request);
		return authorizationHeader;
	}

	/**
	 * Checks if the current request is a is a public request
	 *
	 * @param request
	 *            the request
	 * @return true, if the current request is a is a public request
	 */
	protected boolean isPublicRequest(@NonNull final HttpServletRequest request)
	{
		boolean isPublicRequest = false;
		String path = getPath(request);
		if (isPublicPath(path) || isSigninPath(path))
		{
			isPublicRequest = true;
		}
		return isPublicRequest;
	}

	/**
	 * Checks if the current request is a is a sign request
	 *
	 * @param request
	 *            the request
	 * @return true, if the current request is a is a sign request
	 */
	protected boolean isSigninRequest(@NonNull final HttpServletRequest request)
	{
		boolean isSigninRequest = false;
		// check the request url path, if it is a sign in request
		String path = getPath(request);
		if (isSigninPath(path))
		{
			isSigninRequest = true;
		}
		return isSigninRequest;
	}

	/**
	 * Checks if the given path is a sign in path. Overwrite this method to provide specific sign in
	 * path for your application.
	 *
	 * @param path
	 *            the sign in path to check.
	 * @return true, if the given path is a sign in path otherwise false.
	 */
	protected boolean isSigninPath(final String path)
	{
		return applicationProperties.getSigninPathPatterns().contains(path);
	}

	/**
	 * Checks if the given path is a public path. Overwrite this method to provide specific public
	 * path for your application.
	 *
	 * @param path
	 *            the sign in path to check.
	 * @return true, if the given path is a public path otherwise false.
	 */
	protected boolean isPublicPath(final String path)
	{
		return applicationProperties.getPublicPathPatterns().contains(path);
	}

	public String getPath(@NonNull final HttpServletRequest request)
	{
		return HttpServletRequestExtensions.getPath(request);
	}

}
