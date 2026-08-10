package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Business object for updating an existing FaceSet.
 * Allows changing the display name, outer_id, tags, and user data.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#updateFaceset(FacesetUpdateBo)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class FacesetUpdateBo {

	/**
	 * FaceSet token identifier.
	 */
	@JsonProperty("faceset_token")
	private String faceToken;

	/**
	 * Current globally unique custom identifier for the FaceSet. Max 255 characters.
	 */
	@JsonProperty("outer_id")
	private String outerId;

	/**
	 * New globally unique custom identifier for the FaceSet. Max 255 characters.
	 */
	@JsonProperty("new_outer_id")
	private String newOuterId;

	/**
	 * New display name for the FaceSet. Max 256 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("display_name")
	private String displayName;

	/**
	 * New comma-separated custom tags for the FaceSet. Max 255 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("tags")
	private String tags;

	/**
	 * New custom user data. Max 16 KB. Cannot include ^@,&=*'"
	 */
	@JsonProperty("user_data")
	private String userData;

}
