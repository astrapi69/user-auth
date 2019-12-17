package de.alpharogroup.user.auth.enums;

public enum HeaderKeyNames
{
	HEADER_KEY_AUTHORIZATION(HeaderKeyNames.AUTHORIZATION),
	HEADER_VALUE_BEARER_PREFIX(HeaderKeyNames.BEARER_PREFIX);
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_PREFIX = "Bearer ";

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
