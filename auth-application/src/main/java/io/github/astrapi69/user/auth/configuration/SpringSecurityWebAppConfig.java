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
package io.github.astrapi69.user.auth.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@Order(SecurityProperties.IGNORED_ORDER)
public class SpringSecurityWebAppConfig
{

	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
		throws Exception
	{
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth, AuthenticationConfiguration config)
		throws Exception
	{
		auth.parentAuthenticationManager(authenticationManager(config))
			.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception
	{
		List<String> signinPaths = applicationProperties.getSigninPathPatterns();
		List<String> ignorePathPatterns = applicationProperties.getIgnorePathPatterns();
		signinPaths.addAll(ignorePathPatterns);
		String[] allPublicPaths = ListExtensions.toArray(signinPaths);
		// @formatter:off
		http
			.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
			.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

			.authorizeRequests()
				.requestMatchers(allPublicPaths).permitAll()
				.anyRequest().authenticated()
	            .and()
			.authenticationEntryPoint(authenticationEntryPoint);
        // @formatter:on
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll()
				.anyRequest().authenticated())
			.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	public void configure(WebSecurity web)
	{
		List<String> publicPaths = applicationProperties.getPublicPathPatterns();
		String[] allIgnorePatterns = ListExtensions.toArray(publicPaths);
		web.ignoring().requestMatchers(allIgnorePatterns);
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder(11);
	}

}
