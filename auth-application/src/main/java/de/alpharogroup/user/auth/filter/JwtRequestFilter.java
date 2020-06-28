package de.alpharogroup.user.auth.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.alpharogroup.json.JsonStringToObjectExtensions;
import de.alpharogroup.json.factory.ObjectMapperFactory;
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

import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.collections.map.MapFactory;
import de.alpharogroup.collections.pairs.KeyValuePair;
import de.alpharogroup.servlet.extensions.HttpServletRequestExtensions;
import de.alpharogroup.servlet.extensions.enums.HeaderKeyNames;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.JwtTokenService;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.jwt.JwtUserDetailsService;
import lombok.NonNull;

@Component public class JwtRequestFilter extends OncePerRequestFilter
{
	@Autowired private JwtUserDetailsService jwtUserDetailsService;
	@Autowired private JwtTokenService jwtTokenService;
	@Autowired private AuthenticationsService authenticationsService;
	@Autowired ApplicationProperties applicationProperties;

	@Override protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain) throws ServletException, IOException
	{
		if(!request.isSecure() || isPublicRequest(request) ){
			chain.doFilter(request, response);
			return;
		}
		String username;
		String password;
		String jwtToken;
		Optional<String> optionalToken = getJwtToken(request);
		if(optionalToken.isPresent()){
			jwtToken = optionalToken.get();
			validateToken(request, response, jwtToken);
		} else if (isSigninRequest(request))
		{
			String payloadRequest = HttpServletRequestExtensions.getBody(request);
			if(payloadRequest.isEmpty()){
				username = request.getParameter("username");
				password = request.getParameter("password");
				if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
					chain.doFilter(request, response);
					return;
				}
			}else{
				ObjectMapper mapper = ObjectMapperFactory.newObjectMapper(MapFactory.newHashMap(
					KeyValuePair.<JsonParser.Feature, Boolean>builder()
						.key(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES).value(true).build()));
				JwtRequest jwtRequest = JsonStringToObjectExtensions
					.toObject(payloadRequest, JwtRequest.class, mapper);
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
		if (username != null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			if (jwtTokenService.validate(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				response.addHeader(HeaderKeyNames.AUTHORIZATION, HeaderKeyNames.BEARER_PREFIX + jwtToken);
			}
		}
	}

	private void setNewToken(HttpServletRequest request, HttpServletResponse response, String username,
		String password)
	{
		String jwtToken;
		UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
		AuthenticationResult<Users, AuthenticationErrors> authenticationResult = authenticationsService
			.authenticate(username, password);
		if(authenticationResult.isValid()){
			jwtToken = jwtTokenService.newJwtToken(userDetails);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
			usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext()
				.setAuthentication(usernamePasswordAuthenticationToken);
			response.addHeader(
				HeaderKeyNames.AUTHORIZATION, HeaderKeyNames.BEARER_PREFIX + jwtToken);
		}
	}

	protected Optional<String> getJwtToken(@NonNull final HttpServletRequest request)
	{
		Optional<String> authorizationHeader = HttpServletRequestExtensions
			.getAuthorizationHeader(request);
		return authorizationHeader;
	}

	protected boolean isPublicRequest(@NonNull final HttpServletRequest request){
		boolean isPublicRequest = false;
		if(isPublicPath(getPath(request))){
			isPublicRequest = true;
		}
		return isPublicRequest;
	}

	/**
	 * Checks if the current request is a is a sign request
	 *
	 * @param request the request
	 * @return true, if the current request is a is a sign request
	 */
	protected boolean isSigninRequest(@NonNull final HttpServletRequest request)
	{
		boolean isSigninRequest = false;
		// check the request url path, if it is a sign in request
		if(isSigninPath(getPath(request))){
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

	public String getPath(@NonNull final HttpServletRequest request){
		return HttpServletRequestExtensions.getPath(request);
	}

}