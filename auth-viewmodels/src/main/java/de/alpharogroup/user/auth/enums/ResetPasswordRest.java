package de.alpharogroup.user.auth.enums;

public enum ResetPasswordRest
{
	MAIN_REST_PATH(ResetPasswordRest.MAIN_PATH),
	EMAIL_REST_PATH(ResetPasswordRest.EMAIL_PATH),
	NEW_PASSWORD_REST_PATH(ResetPasswordRest.NEW_PASSWORD_PATH),
	VERIFY_TOKEN_REST_PATH(ResetPasswordRest.VERIFY_TOKEN_PATH);
	public static final String MAIN_PATH = "/resetpassword";
	public static final String EMAIL_PATH = "/email";
	public static final String NEW_PASSWORD_PATH = "/newpassword";
	public static final String VERIFY_TOKEN_PATH = "/token";

	private final String value;

	ResetPasswordRest(final String value)
	{
		this.value = value;
	}
}
