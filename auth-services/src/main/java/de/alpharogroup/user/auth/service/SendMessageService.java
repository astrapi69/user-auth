package de.alpharogroup.user.auth.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;

import de.alpharogroup.email.messages.EmailConstants;
import de.alpharogroup.email.messages.EmailMessage;
import de.alpharogroup.email.send.SendEmail;
import de.alpharogroup.email.utils.EmailExtensions;
import de.alpharogroup.string.StringExtensions;
import io.github.astrapi69.message.mail.viewmodel.InfoMessage;

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
