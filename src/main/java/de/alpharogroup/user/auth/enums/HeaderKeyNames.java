package de.alpharogroup.user.auth.enums;

public enum HeaderKeyNames
{
	HEADER_KEY_AUTHORIZATION_VALUE(HeaderKeyNames.AUTHORIZATION),
	HEADER_VALUE_BEARER_PREFIX_VALUE(HeaderKeyNames.BEARER_PREFIX),
	DEFAULT_TOKEN_TYPE_VALUE_VALUE(HeaderKeyNames.DEFAULT_TOKEN_TYPE),
	DEFAULT_TOKEN_ISSUER_VALUE(HeaderKeyNames.DEFAULT_TOKEN_ISSUER),
	DEFAULT_TOKEN_AUDIENCE_VALUE(HeaderKeyNames.DEFAULT_TOKEN_AUDIENCE),
	TOKEN_TYPE_KEY_VALUE(HeaderKeyNames.TOKEN_TYPE_KEY),
	HEADER_KEY_ROLES_VALUE(HeaderKeyNames.HEADER_KEY_ROLES),
	DEFAULT_DURABILITY_VALUE(HeaderKeyNames.DEFAULT_DURABILITY);
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final String DEFAULT_TOKEN_TYPE = "JWT";
	public static final String TOKEN_TYPE_KEY = "token-type";
	public static final String HEADER_KEY_ROLES = "u-roles";
	public static final String DEFAULT_DURABILITY = "432000000";
	public static final String DEFAULT_TOKEN_ISSUER = "user-auth-api";
	public static final String DEFAULT_TOKEN_AUDIENCE = "user-auth-app";

	private final String name;

	HeaderKeyNames(final String name)
	{
		this.name = name;
	}

	/**
	 * Gets the specific name
	 *
	 * @return the specific name
	 */
	public String getName()
	{
		return name;
	}
}
