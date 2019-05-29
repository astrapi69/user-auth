package de.alpharogroup.user.auth.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@ComponentScan(basePackages = "de.alpharogroup.user.auth")
@EntityScan(basePackages = "de.alpharogroup.user.auth.jpa.entities")
@EnableJpaRepositories(basePackages = { "de.alpharogroup.user.auth.jpa.repositories" })
@EnableTransactionManagement
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Config implements WebMvcConfigurer
{

	@SuppressWarnings("unused")
	Environment env;

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedOrigins("*");
	}

	@Bean
	public MessageSource messageSource()
	{

		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/errors");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}