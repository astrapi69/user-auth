package de.alpharogroup.user.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration @EnableSwagger2 public class SwaggerConfiguration
	extends AbstractSwaggerConfiguration
{

	@Bean public Docket api()
	{
		return super.api();
	}

	protected ApiInfo metaData()
	{
		return super.metaData();
	}

	@Override public String getBasePackage()
	{
		return "de.alpharogroup.user.auth";
	}

	@Override public String getApiInfoTitle()
	{
		return "User Auth REST API";
	}

	@Override public String getApiInfoDescription()
	{
		return "REST API for user authorisation and authentication";
	}

	@Override public String getApiInfoVersion()
	{
		return ApplicationConfiguration.VERSION_API_1;
	}

	@Override public String getContactName()
	{
		return "user-auth org.";
	}

	@Override public String getContactUrl()
	{
		return "www.user-auth.org";
	}

	@Override public String getDocketPathsPathRegex()
	{
		return ApplicationConfiguration.REST_VERSION + "/.*|";
	}

}
