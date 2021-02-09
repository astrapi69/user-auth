package de.alpharogroup.user.auth.mapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class WiserStarter
{

	@SuppressWarnings("unused")
	public static void main(final String[] args) throws MessagingException
	{
		final Wiser smtpServer = new Wiser();

		smtpServer.setPort(2500); // Default is 25

		smtpServer.start();

		for (final WiserMessage message : smtpServer.getMessages())
		{
			final String envelopeSender = message.getEnvelopeSender();
			final String envelopeReceiver = message.getEnvelopeReceiver();

			final MimeMessage mess = message.getMimeMessage();

			// now do something fun!
			System.out.println(mess.toString());
		}
	}

}
