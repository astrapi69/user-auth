package de.alpharogroup.user.auth.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.net.ssl.SSLException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.alpharogroup.auth.beans.AuthenticationResult;
import de.alpharogroup.auth.enums.AuthenticationErrors;
import de.alpharogroup.collections.map.MapFactory;
import de.alpharogroup.collections.pairs.KeyValuePair;
import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.configuration.JwtTokenUtil;
import de.alpharogroup.user.auth.controller.JwtAuthenticationController;
import de.alpharogroup.user.auth.dto.JwtRequest;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.service.api.AuthenticationsService;
import de.alpharogroup.user.auth.service.jwt.JwtUserDetailsService;
import de.alpharogroup.xml.json.JsonStringToObjectExtensions;
import de.alpharogroup.xml.json.ObjectMapperFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

@Component public class JwtRequestFilter extends OncePerRequestFilter
{
	public static final String AUTHENTICATE_PATH = ApplicationConfiguration.REST_VERSION + JwtAuthenticationController.REST_PATH + JwtAuthenticationController.AUTHENTICATE;
	@Autowired private JwtUserDetailsService jwtUserDetailsService;
	@Autowired private JwtTokenUtil jwtTokenUtil;

	@Autowired private AuthenticationsService authenticationsService;

	@Override protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain) throws ServletException, IOException
	{
		String username = null;
		String jwtToken = null;
		String path = getPath(request);
		if(AUTHENTICATE_PATH.equals(path)){
			String payloadRequest = getBody(request);
			ObjectMapper mapper = ObjectMapperFactory
				.newObjectMapper(
					MapFactory.newHashMap(
						KeyValuePair.<JsonParser.Feature, Boolean>builder()
				.key(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
							.value(true).build()));
			JwtRequest jwtRequest = JsonStringToObjectExtensions
				.toObject(payloadRequest, JwtRequest.class, mapper);
			username = jwtRequest.getUsername();
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			AuthenticationResult<Users, AuthenticationErrors> authenticationResult = authenticationsService
				.authenticate(username, jwtRequest.getPassword());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if(authentication != null){
				jwtToken = jwtTokenUtil.generateToken(userDetails);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext()
					.setAuthentication(usernamePasswordAuthenticationToken);
			} else{

			}
		} else{
			final String requestTokenHeader = request.getHeader("Authorization");

			// JWT Token is in the form "Bearer token". Remove Bearer word and get
			// only the Token
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
			{
				jwtToken = requestTokenHeader.substring(7);
				try
				{
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				}
				catch (IllegalArgumentException e)
				{
					System.out.println("Unable to get JWT Token");
				}
				catch (ExpiredJwtException e)
				{
					System.out.println("JWT Token has expired");
				}
			}
			else
			{
				logger.warn("JWT Token does not begin with Bearer String");
			}
			// Once we get the token validate it.
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				// if token is valid configure Spring Security to manually set
				// authentication
				if (jwtTokenUtil.validateToken(jwtToken, userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext()
						.setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Checks if the current request is a is a sign request
	 *
	 * @param request
	 *            the request
	 * @return true, if the current request is a is a sign request
	 * @throws SSLException
	 *             occurs if the scheme is not https
	 */
	protected boolean isSigninRequest(@NonNull final HttpServletRequest request) throws SSLException
	{
		boolean isSigninRequest = false;
		// check the request url path, if it is a sign in request
		if(isSigninPath(getPath(request))){
			// check if scheme is https
			if (!isSecureRequest(request))
			{
				throw new SSLException("use https scheme");
			}
			isSigninRequest = true;
		}
		return isSigninRequest;
	}

	/**
	 * Checks if the current request is a secure request, means that the scheme is https
	 *
	 * @param request the request
	 * @return true, if is secure request
	 */
	protected boolean isSecureRequest(@NonNull final HttpServletRequest request){
		return request.isSecure();
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
		return AUTHENTICATE_PATH.equals(path);
	}

	public static String getPath(@NonNull final HttpServletRequest request){
		return request.getRequestURI().substring(request.getContextPath().length());
	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}
}