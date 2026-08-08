package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Business object for creating a new FaceSet.
 * Contains the display name, outer_id, tags, initial face tokens,
 * user data, and force-merge flag.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#createFaceset(FacesetBo)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class FacesetBo {

	/**
	 * Display name for the FaceSet. Max 256 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("display_name")
    private String displayName;

	/**
	 * Globally unique custom identifier for the FaceSet. Max 255 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("outer_id")
	private String outerId;

	/**
	 * Comma-separated custom tags for grouping FaceSets. Max 255 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("tags")
	private String tags;

	/**
	 * Comma-separated face tokens to add to the FaceSet. Max 5 face tokens.
	 */
	@JsonProperty("face_tokens")
	private String faceTokens;

	/**
	 * Custom user data. Max 16 KB. Cannot include ^@,&=*'"
	 */
	@JsonProperty("user_data")
	private String userData;

	/**
	 * Whether to merge face_tokens into an existing FaceSet with the same outer_id.
	 * 0 = return FACESET_EXIST error, 1 = merge into existing. Default: 0.
	 */
	@JsonProperty("force_merge")
	private int forceMerge;

}
