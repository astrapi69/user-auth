package io.github.astrapi69.user.auth.configuration;

import io.github.astrapi69.user.auth.enums.AppRestPath;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration
{

	@Autowired
	ApplicationProperties applicationProperties;

	@Bean
	public OpenAPI customOpenAPI()
	{
		return new OpenAPI().info(new Info().title(newApiInfoTitle())
			.description(newApiInfoDescription()).version(newApiInfoVersion())
			.license(new License().name(newApiInfoLicense()).url(newApiInfoLicenseUrl()))
			.contact(new Contact().name(newContactName()).url(newContactUrl())));
	}

	public String newBasePackage()
	{
		return applicationProperties.getBasePackage() != null
			? applicationProperties.getBasePackage()
			: "io.github.astrapi69.user.auth";
	}

	public String newApiInfoTitle()
	{
		return applicationProperties.getApiInfoTitle() != null
			? applicationProperties.getApiInfoTitle()
			: "User Authorization and Authentication REST API";
	}

	public String newApiInfoDescription()
	{
		return applicationProperties.getApiInfoDescription() != null
			? applicationProperties.getApiInfoDescription()
			: "REST API for user authorization and authentication";
	}

	public String newApiInfoVersion()
	{
		return applicationProperties.getApiInfoVersion() != null
			? applicationProperties.getApiInfoVersion()
			: AppRestPath.REST_API_VERSION_1;
	}

	public String newApiInfoLicense()
	{
		return applicationProperties.getApiInfoLicense() != null
			? applicationProperties.getApiInfoLicense()
			: "MIT";
	}

	public String newApiInfoLicenseUrl()
	{
		return applicationProperties.getApiInfoLicenseUrl() != null
			? applicationProperties.getApiInfoLicenseUrl()
			: "https://opensource.org/licenses/MIT";
	}

	public String newContactName()
	{
		return applicationProperties.getContactName() != null
			? applicationProperties.getContactName()
			: "User Authentication Inc.";
	}

	public String newContactUrl()
	{
		return applicationProperties.getContactUrl() != null
			? applicationProperties.getContactUrl()
			: "www.user-auth.org";
	}

	public String newDocketPathsRegex()
	{
		return applicationProperties.getDocketPathsRegex() != null
			? applicationProperties.getDocketPathsRegex()
			: AppRestPath.REST_DOCKET_PATHS_REGEX;
	}
}
