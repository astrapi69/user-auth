package io.github.astrapi69.user.auth.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.github.astrapi69.collection.list.ListExtensions;
import io.github.astrapi69.user.auth.entrypoint.RestAuthenticationEntryPoint;
import io.github.astrapi69.user.auth.filter.CorsFilter;
import io.github.astrapi69.user.auth.filter.JwtRequestFilter;
import io.github.astrapi69.user.auth.service.jwt.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SpringSecurityWebAppConfig
{

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		List<String> signinPaths = applicationProperties.getSigninPathPatterns();
		List<String> ignorePathPatterns = applicationProperties.getIgnorePathPatterns();
		signinPaths.addAll(ignorePathPatterns);
		String[] allPublicPaths = ListExtensions.toArray(signinPaths);

		// @formatter:off
		http
				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers(allPublicPaths).permitAll()  // Changed from antMatchers to requestMatchers
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint);
		// @formatter:on
		return http.build();
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder(11);
	}
}
