package de.alpharogroup.user.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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

	String dir;

	String name;
	@NestedConfigurationProperty
	final Db db = new Db();

	@Getter
	@Setter
	static class Db
	{
		String name;
		String host;
		@NestedConfigurationProperty
		final Postgres postgres = new Postgres();

		@Getter
		@Setter
		static class Postgres
		{
			@NestedConfigurationProperty
			final Url url = new Url();

			@Getter
			@Setter
			static class Url
			{
				String prefix;
				int port;
			}
		}
	}
}