package de.alpharogroup.user.auth;

import de.alpharogroup.user.auth.service.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.spring.boot.application.ApplicationHooks;
import de.alpharogroup.user.auth.configuration.ApplicationProperties;

@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class, JwtProperties.class })
@SpringBootApplication
public class UserAuthApplication
{

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(UserAuthApplication.class);
		ApplicationHooks instance = ApplicationHooks.INSTANCE;
		instance.addDatabaseIfNotExists(application, PathFinder.getSrcMainResourcesDir(),
			"application-dev.yml");
		application.run(args);
	}

}