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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.github.astrapi69.collection.list.ListExtensions;
import io.github.astrapi69.user.auth.entrypoint.RestAuthenticationEntryPoint;
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
	public org.springframework.web.filter.CorsFilter applicationCorsFilter()
	{
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new org.springframework.web.filter.CorsFilter(source);
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		List<String> signinPaths = applicationProperties.getSigninPathPatterns();
		List<String> ignorePathPatterns = applicationProperties.getIgnorePathPatterns();
		signinPaths.addAll(ignorePathPatterns);
		String[] allPublicPaths = ListExtensions.toArray(signinPaths);
		CorsFilter corsFilter = applicationCorsFilter();
		// @formatter:off
		http
				.addFilterBefore(corsFilter, ChannelProcessingFilter.class) // Injected bean here
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf(csrf -> csrf.ignoringRequestMatchers(allPublicPaths)) .authorizeHttpRequests(auth -> auth
						.requestMatchers(allPublicPaths).permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(authenticationEntryPoint)
				);
		// @formatter:on

		return http.build();
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder(11);
	}

}
