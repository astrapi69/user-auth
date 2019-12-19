package de.alpharogroup.user.auth.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import de.alpharogroup.collections.list.ListExtensions;
import de.alpharogroup.user.auth.entrypoint.RestAuthenticationEntryPoint;
import de.alpharogroup.user.auth.filter.JwtRequestFilter;
import de.alpharogroup.user.auth.handler.SigninFailureHandler;
import de.alpharogroup.user.auth.handler.SigninSuccessHandler;
import de.alpharogroup.user.auth.handler.SignoutSuccessHandler;
import de.alpharogroup.user.auth.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
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
	SigninFailureHandler signinFailureHandler;

	@Autowired
	SigninSuccessHandler signinSuccessHandler;

	@Autowired
	SignoutSuccessHandler signoutSuccessHandler;

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
		String[] allPublicPaths = ListExtensions.toArray(signinPaths);
		// @formatter:off
		http
			.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(allPublicPaths).permitAll()
				.anyRequest().authenticated()
	            .and().csrf().disable()
	            .exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
	            .and().formLogin().successHandler(signinSuccessHandler)
	            .and().formLogin().failureHandler(signinFailureHandler);
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