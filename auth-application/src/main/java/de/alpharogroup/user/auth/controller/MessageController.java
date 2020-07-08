package de.alpharogroup.user.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.alpharogroup.user.auth.configuration.ApplicationConfiguration;

@RestController
@RequestMapping(ApplicationConfiguration.REST_VERSION + MessageController.REST_PATH)
@CrossOrigin
public class MessageController
{

	public static final String REST_PATH = "/jwt";
	public static final String PRIVATE_PATH = "/private";
	public static final String ISPUBLIC_PATH = "/ispublic";
	/**
	 * Call this link <a href="https://localhost:8443/v1/jwt/private"></a>
	 */
	@RequestMapping(value = PRIVATE_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getPrivateMessage() {
		return ResponseEntity.ok("This is a private message");
	}

	@RequestMapping(value = ISPUBLIC_PATH, method = RequestMethod.GET)
	public ResponseEntity<?> getPublicMessage() {
		return ResponseEntity.ok("This is a public message");
	}

}