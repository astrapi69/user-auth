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

import io.github.astrapi69.user.auth.enums.AppRestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import io.github.astrapi69.spring.configuration.AbstractSwaggerConfiguration;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends AbstractSwaggerConfiguration
{
	@Autowired
	ApplicationProperties applicationProperties;

	@Bean
	public Docket api()
	{
		return super.api();
	}

	protected ApiInfo metaData()
	{
		return super.metaData();
	}

	@Override
	public String newBasePackage()
	{
		return applicationProperties.getBasePackage() != null
			? applicationProperties.getBasePackage()
			: "io.github.astrapi69.user.auth";
	}

	@Override
	public String newApiInfoTitle()
	{
		return applicationProperties.getApiInfoTitle() != null
			? applicationProperties.getApiInfoTitle()
			: "user authorisation and authentication REST API";
	}

	@Override
	public String newApiInfoDescription()
	{
		return applicationProperties.getApiInfoDescription() != null
			? applicationProperties.getApiInfoDescription()
			: "REST API for user authorisation and authentication";
	}

	@Override
	public String newApiInfoVersion()
	{
		return applicationProperties.getApiInfoVersion() != null
			? applicationProperties.getApiInfoVersion()
			: AppRestPath.REST_API_VERSION_1;
	}

	@Override
	public String newApiInfoLicense()
	{
		return applicationProperties.getApiInfoLicense() != null
			? applicationProperties.getApiInfoLicense()
			: "MIT";
	}

	@Override
	public String newApiInfoLicenseUrl()
	{
		return applicationProperties.getApiInfoLicenseUrl() != null
			? applicationProperties.getApiInfoLicenseUrl()
			: "https://opensource.org/licenses/MIT";
	}

	@Override
	public String newContactName()
	{
		return applicationProperties.getContactName() != null
			? applicationProperties.getContactName()
			: "user authentication inc.";
	}

	@Override
	public String newContactUrl()
	{
		return applicationProperties.getContactUrl() != null
			? applicationProperties.getContactUrl()
			: "www.user-auth.org";
	}

	@Override
	public String newDocketPathsRegex()
	{
		return applicationProperties.getDocketPathsRegex() != null
			? applicationProperties.getDocketPathsRegex()
			: AppRestPath.REST_DOCKET_PATHS_REGEX;
	}

}
