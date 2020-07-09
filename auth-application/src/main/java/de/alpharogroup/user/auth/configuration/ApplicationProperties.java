package de.alpharogroup.user.auth.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationProperties
{

	String uriScheme;
	String host;
	String dbHost;
	String dbName;
	int dbPort;
	String dbUrlPrefix;
	String contextPath;
	String signinFailedPage;
	String dir;
	String name;
	List<String> publicPathPatterns;
	List<String> signinPathPatterns;
	List<String> ignorePathPatterns;

}