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
package io.github.astrapi69.user.auth;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.spring.boot.application.ApplicationHooks;
import io.github.astrapi69.user.auth.configuration.ApplicationProperties;
import io.github.astrapi69.user.auth.service.jwt.JwtProperties;

@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class, JwtProperties.class })
@SpringBootApplication
public class UserAuthApplication
{

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(UserAuthApplication.class);
		ApplicationHooks instance = ApplicationHooks.INSTANCE;
		File projectSrcMainResourcesDir = PathFinder.getRelativePath(
			PathFinder.getProjectDirectory(), "auth-application", "src", "main", "resources");
		instance.addDatabaseIfNotExists(application, projectSrcMainResourcesDir,
			"application-dev.yml");
		application.run(args);
	}

}
