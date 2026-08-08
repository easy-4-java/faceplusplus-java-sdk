package com.faceplusplus.spring.boot;


import lombok.Data;

/**
 * Configuration properties for the Face++ SDK.
 * Properties are bound from the Spring Boot configuration prefix {@value #PREFIX}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppTemplate
 */
@Data
public class FaceppProperties {

	/**
	 * The prefix of the property of {@link FaceppProperties}.
	 */
	public static final String PREFIX = "faceplusplus.facepp";

	/** Base host URL for the Face++ API. Defaults to {@code https://api-cn.faceplusplus.com}. */
	private String host = "https://api-cn.faceplusplus.com";

	/** The API key (appId) used for authentication with the Face++ service. */
	private String appId;

	/** The API secret (appCertificate) used for authentication with the Face++ service. */
	private String appCertificate;

	/** Token expiration time in seconds. Defaults to 3600. */
	private int expirationTimeInSeconds = 3600;

	/** RESTful login key for Agora service integration. */
	private String loginKey;

	/** RESTful login secret for Agora service integration. */
	private String loginSecret;

	/** Recording region selector (e.g. 7 = Hong Kong, 10 = Singapore). */
	private Integer ossRegion;

	/** Video width in pixels for Agora integration. */
	private Integer viewWidth;

	/** Video height in pixels for Agora integration. */
	private Integer viewHeight;

}
