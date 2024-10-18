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
package io.github.astrapi69.user.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;

import io.github.astrapi69.email.messages.EmailConstants;
import io.github.astrapi69.email.messages.EmailMessage;
import io.github.astrapi69.email.send.SendEmail;
import io.github.astrapi69.email.utils.EmailExtensions;
import io.github.astrapi69.message.mail.viewmodel.InfoMessage;
import io.github.astrapi69.string.StringExtensions;

/**
 * The Class {@link SendMessageService} provides methods for send emails
 */
public class SendMessageService
{

	/**
	 * Send email with default email headers.
	 *
	 * @param emailSender
	 *            the email sender
	 * @param senderEmail
	 *            the sender email
	 * @param senderPersonal
	 *            the sender personal
	 * @param recipientEmail
	 *            the recipient email
	 * @param recipientPersonal
	 *            the recipient personal
	 * @param subject
	 *            the subject
	 * @param content
	 *            the content
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void sendEmail(final SendEmail emailSender, final String senderEmail,
		final String senderPersonal, final String recipientEmail, final String recipientPersonal,
		final String subject, final String content)
		throws MessagingException, UnsupportedEncodingException
	{
		sendEmail(emailSender, senderEmail, senderPersonal, recipientEmail, recipientPersonal,
			subject, content, null);
	}

	/**
	 * Send email with the given arguments.
	 *
	 * @param emailSender
	 *            the email sender
	 * @param senderEmail
	 *            the sender email
	 * @param senderPersonal
	 *            the sender personal
	 * @param recipientEmail
	 *            the recipient email
	 * @param recipientPersonal
	 *            the recipient personal
	 * @param subject
	 *            the subject
	 * @param content
	 *            the content
	 * @param emailHeaders
	 *            the email headers
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void sendEmail(final SendEmail emailSender, final String senderEmail,
		final String senderPersonal, final String recipientEmail, final String recipientPersonal,
		final String subject, final String content, final Map<String, String> emailHeaders)
		throws MessagingException, UnsupportedEncodingException
	{
		sendEmail(emailSender, senderEmail, senderPersonal, recipientEmail, recipientPersonal,
			subject, content, emailHeaders, false);
	}

	/**
	 * Send email with the given arguments.
	 *
	 * @param emailSender
	 *            the email sender
	 * @param senderEmail
	 *            the sender email
	 * @param senderPersonal
	 *            the sender personal
	 * @param recipientEmail
	 *            the recipient email
	 * @param recipientPersonal
	 *            the recipient personal
	 * @param subject
	 *            the subject
	 * @param content
	 *            the content
	 * @param emailHeaders
	 *            the email headers
	 * @param withSession
	 *            the flag if the message will be created with the mail session of the
	 *            {@link SendEmail}
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void sendEmail(final SendEmail emailSender, final String senderEmail,
		final String senderPersonal, final String recipientEmail, final String recipientPersonal,
		String subject, final String content, final Map<String, String> emailHeaders,
		final boolean withSession) throws MessagingException, UnsupportedEncodingException
	{
		final EmailMessage emailMessage;
		if (withSession)
		{
			emailMessage = new EmailMessage(emailSender.getSession());
		}
		else
		{
			emailMessage = new EmailMessage();
		}
		// Set the sender...
		EmailExtensions.setFromToEmailMessage(senderEmail, senderPersonal,
			EmailConstants.CHARSET_UTF8, emailMessage);
		// Set recipient
		EmailExtensions.addToRecipientToEmailMessage(recipientEmail, recipientPersonal,
			EmailConstants.CHARSET_UTF8, emailMessage);
		// Set subject
		// Remove new line characters from subject. If the subject contains new
		// line characters a strange behavior occurs...
		subject = StringExtensions.removeNewlineCharacters(subject);
		emailMessage.setSubject(subject);
		// Set content...
		emailMessage.setUtf8Content(content);
		// Set email header
		if (emailHeaders != null && !emailHeaders.isEmpty())
		{
			for (final Entry<String, String> emailHeaderEntry : emailHeaders.entrySet())
			{
				emailMessage.setHeader(emailHeaderEntry.getKey(), emailHeaderEntry.getValue());
			}
		}
		emailSender.sendEmailMessage(emailMessage);
	}

	/**
	 * Send info email.
	 *
	 * @param emailSender
	 *            the email sender
	 * @param model
	 *            the model
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void sendInfoEmail(final SendEmail emailSender, final InfoMessage model)
		throws MessagingException, UnsupportedEncodingException
	{
		sendInfoEmail(emailSender, model, null);
	}

	/**
	 * Send info email.
	 *
	 * @param emailSender
	 *            the email sender
	 * @param model
	 *            the model
	 * @param emailHeaders
	 *            the email headers
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public static void sendInfoEmail(final SendEmail emailSender, final InfoMessage model,
		final Map<String, String> emailHeaders)
		throws MessagingException, UnsupportedEncodingException
	{
		sendEmail(emailSender, model.getApplicationSenderAddress(),
			model.getApplicationDomainName(), model.getRecipientEmailContact(),
			model.getRecipientFullName(), model.getMessageContentModel().getSubject(),
			model.getMessageContentModel().getContent(), emailHeaders);
	}

}
