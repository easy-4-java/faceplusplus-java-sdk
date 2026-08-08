package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Business object for deleting a FaceSet.
 * Supports deletion by outer_id with optional check-empty behavior.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#deleteFaceset(FacesetDeleteBo)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
public class FacesetDeleteBo {

	/**
	 * Globally unique custom identifier for the FaceSet. Max 255 characters. Cannot include ^@,&=*'"
	 */
	@JsonProperty("outer_id")
	private String outerId;

	/**
	 * Comma-separated face tokens. Max 5 face tokens.
	 */
	@JsonProperty("face_tokens")
	private String faceTokens;

	/**
	 * Whether to check if the FaceSet contains face tokens before deletion.
	 * 0 = do not check, 1 = check (prevent deletion if faces exist). Default: 1.
	 */
	@JsonProperty("check_empty")
	private int checkEmpty = 1;

}
