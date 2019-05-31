package de.alpharogroup.user.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.alpharogroup.user.auth.configuration.ApplicationProperties;

@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class })
@SpringBootApplication
public class UserAuthApplication
{

	public static void main(String[] args)
	{
		SpringApplication sa = new SpringApplication(UserAuthApplication.class);
		sa.run(args);
	}

}
