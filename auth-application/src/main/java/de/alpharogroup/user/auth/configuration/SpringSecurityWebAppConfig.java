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
package de.alpharogroup.user.auth.configuration;

import java.util.Arrays;
import java.util.List;

import de.alpharogroup.user.auth.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import de.alpharogroup.collections.list.ListExtensions;
import de.alpharogroup.user.auth.entrypoint.RestAuthenticationEntryPoint;
import de.alpharogroup.user.auth.filter.JwtRequestFilter;
import de.alpharogroup.user.auth.service.UserDetailsServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.IGNORED_ORDER)
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter
{

	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.parentAuthenticationManager(authenticationManagerBean())
			.userDetailsService(userDetailsService);
	}

	@Override
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
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(allPublicPaths).permitAll()
				.anyRequest().authenticated()
	            .and().csrf().disable()
	            .exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint);
        // @formatter:on
	}

	@Override
	public void configure(WebSecurity web)
	{
		List<String> publicPaths = applicationProperties.getPublicPathPatterns();
		String[] allIgnorePatterns = ListExtensions.toArray(publicPaths);
		web.ignoring().antMatchers(allIgnorePatterns);
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder(11);
	}

}
