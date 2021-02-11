package de.alpharogroup.user.auth.enums;

public enum Rest
{
	VERSION_1_PATH(Rest.VERSION_1);
	public static final String VERSION_1 = "/v1";

	private final String value;
	Rest(final String value){
		this.value = value;
	}
}
