package io.github.astrapi69.user.auth.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * The enum class {@link AppRestPath} holds constants for the application rest paths
 */
@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AppRestPath
{
	/** The enum value for the rest path VERSION. */
	VERSION(AppRestPath.REST_VERSION),
	/** The enum value for the rest path RESOURCE_BUNDLES. */
	RESOURCE_BUNDLES(AppRestPath.REST_RESOURCE_BUNDLES),
	/** The enum value for the rest path COUNTRIES. */
	COUNTRIES(AppRestPath.REST_COUNTRIES),
	/** The enum value for the rest path LANGUAGES. */
	LANGUAGES(AppRestPath.REST_LANGUAGES),
	/** The enum value for the rest path LANGUAGE_LOCALES. */
	LANGUAGE_LOCALES(AppRestPath.REST_LANGUAGE_LOCALES),
	/** The enum value for the rest path BUNDLE_APPLICATIONS. */
	BUNDLE_APPLICATIONS(AppRestPath.REST_BUNDLE_APPLICATIONS),
	/** The enum value for the rest path BUNDLE_NAMES. */
	BUNDLE_NAMES(AppRestPath.REST_BUNDLE_NAMES);

	public static final String SLASH = "/";
	public static final String REST_DOCKET_PATHS_REGEX_SUFFIX = "/.*|";
	public static final String REST_API_VERSION_1 = "v1";
	public static final String REST_VERSION = SLASH + REST_API_VERSION_1;
	public static final String REST_DOCKET_PATHS_REGEX = REST_VERSION
		+ REST_DOCKET_PATHS_REGEX_SUFFIX;
	public static final String REST_RESOURCE_BUNDLES = "/resourcebundles";
	public static final String REST_COUNTRIES = "/countries";
	public static final String REST_LANGUAGES = "/languages";
	public static final String REST_LANGUAGE_LOCALES = "/locales";
	public static final String REST_BUNDLE_APPLICATIONS = "/bundle/applications";
	public static final String REST_BUNDLE_NAMES = "/bundle/names";

	String value;

}
