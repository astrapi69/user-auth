package de.alpharogroup.user.auth.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{
	private static final String VERSION_API_1 = "/v1";

	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2).select()
			.apis(RequestHandlerSelectors.basePackage("de.alpharogroup.user.auth"))
			.paths(regex(VERSION_API_1 + "/.*|")).build().apiInfo(metaData());
	}

	private ApiInfo metaData()
	{
		return new ApiInfo("User Auth REST API",
			"REST API for user authorisation and authentication", "v1", "",
			new Contact("user-auth org.", "www.user-auth.org", ""), "", "",
			Collections.emptyList());
	}

}
