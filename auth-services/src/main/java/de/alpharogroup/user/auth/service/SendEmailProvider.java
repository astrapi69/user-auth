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
package de.alpharogroup.user.auth.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;

import de.alpharogroup.resourcebundle.properties.PropertiesFileExtensions;
import de.alpharogroup.email.messages.EmailConstants;
import de.alpharogroup.email.messages.EmailMessage;
import de.alpharogroup.email.send.SendEmail;
import de.alpharogroup.email.utils.EmailExtensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendEmailProvider
{

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendEmailProvider.class.getName());
	private static final SendEmail emailSender;

	public static Properties emailProperties;
	public static Properties getEmailProperties() {
		return emailProperties;
	}

	static {
		 emailProperties = null;
		try {
			emailProperties = PropertiesFileExtensions.loadProperties("mail.properties");
		} catch (IOException e) {
			LOGGER.error("IOException occured by loading the mail.properties.",
					e);
		}
		emailSender = new SendEmail(emailProperties);
	}

	public static synchronized SendEmail getEmailSender() {
		return emailSender;
	}

	public static void setApplicationAsSender(String senderAddress,
			final String domainName, final EmailMessage emailMessage)
		throws UnsupportedEncodingException, MessagingException
	{
		EmailExtensions.setFromToEmailMessage(senderAddress, domainName,
				EmailConstants.CHARSET_UTF8, emailMessage);
	}

	public static void sendEmail(String applicationSenderAddress,
			final String applicationDomainName, String recipientEmailContact,
			String recipientFullName, String subject, String content)
		throws MessagingException, UnsupportedEncodingException
	{
		final EmailMessage emailMessage = new EmailMessage();
		SendEmailProvider.setApplicationAsSender(applicationSenderAddress,
				applicationDomainName, emailMessage);

		// Set recipient
		EmailExtensions.addToRecipientToEmailMessage(recipientEmailContact,
				recipientFullName, EmailConstants.CHARSET_UTF8, emailMessage);
		// Set subject
		emailMessage.setSubject(subject);
		// Set content...
		emailMessage.setUtf8Content(content);
		final SendEmail sendEmail = getEmailSender();
		sendEmail.sendEmailMessage(emailMessage);
	}

}
