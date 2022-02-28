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
package io.github.astrapi69.user.auth.enums;

public enum ResetPasswordRest
{
	MAIN_REST_PATH(ResetPasswordRest.MAIN_PATH), EMAIL_REST_PATH(
		ResetPasswordRest.EMAIL_PATH), NEW_PASSWORD_REST_PATH(
			ResetPasswordRest.NEW_PASSWORD_PATH), VERIFY_TOKEN_REST_PATH(
				ResetPasswordRest.VERIFY_TOKEN_PATH);

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
