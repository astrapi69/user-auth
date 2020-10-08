package de.alpharogroup.user.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.spring.boot.application.ApplicationHooks;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class })
@SpringBootApplication
public class UserAuthApplication extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(
		SpringApplicationBuilder builder) {
		return builder.sources(UserAuthApplication.class);
	}

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(UserAuthApplication.class);
		ApplicationHooks instance = ApplicationHooks.INSTANCE;
		instance.addDatabaseIfNotExists(application, PathFinder.getSrcMainResourcesDir(),
			"application-dev.yml");
		application.run(args);
	}

}
