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
package de.alpharogroup.user.auth.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;
import de.alpharogroup.user.auth.jpa.entities.Users;
import de.alpharogroup.user.auth.utils.CurrentUserResolver;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + DashboardController.REST_PATH)
@CrossOrigin
public class DashboardController
{

	public static final String REST_PATH = "/dashboard";
	public static final String MEMBER_PATH = "/member";

	@CrossOrigin(origins = "*")
	@RequestMapping(value = MEMBER_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> member(@AuthenticationPrincipal Users user)
	{
		if (user == null)
		{
			Optional<Users> optionalUsers = CurrentUserResolver.getCurrentUser();
			if (optionalUsers.isPresent())
			{
				Users currentUser = optionalUsers.get();
				return ResponseEntity.ok(currentUser);
			}
		}
		return ResponseEntity.ok("Member area");
	}

}
