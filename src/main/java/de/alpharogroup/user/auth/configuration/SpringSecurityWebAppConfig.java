package de.alpharogroup.user.auth.configuration;

import de.alpharogroup.collections.list.ListExtensions;
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

import de.alpharogroup.user.auth.entrypoint.RestAuthenticationEntryPoint;
import de.alpharogroup.user.auth.handler.SigninFailureHandler;
import de.alpharogroup.user.auth.handler.SigninSuccessHandler;
import de.alpharogroup.user.auth.handler.SignoutSuccessHandler;
import de.alpharogroup.user.auth.service.UserDetailsServiceImpl;

import java.util.List;

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
		List<String> publicPaths = applicationProperties.getPublicPaths();
		List<String> signinPaths = applicationProperties.getSigninPaths();
		publicPaths.addAll(signinPaths);
		String[] allPublicPaths = ListExtensions.toArray(publicPaths);
		// @formatter:off
		http.authorizeRequests()
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
		List<String> ignorePatterns = applicationProperties.getIgnorePatterns();
		String[] allIgnorePatterns = ListExtensions.toArray(ignorePatterns);
		web.ignoring().antMatchers(allIgnorePatterns);
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder(11);
	}

}