package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Options for the Face Analyze API.
 * Controls which facial attributes and landmarks are returned.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#analyze(String[], FaceAnalyzeOptions)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
@Builder
public class FaceAnalyzeOptions {

	/**
	 * Whether to detect and return facial landmarks.
	 * Valid values: 0 (no detection), 1 (83 landmarks), 2 (106 landmarks).
	 * Default: 0.
	 */
	@JsonProperty("return_landmark")
    private int returnLandmark;

	/**
	 * Comma-separated list of facial attributes to detect and return.
	 * Valid values include: gender, age, smiling, headpose, facequality, blur,
	 * eyestatus, emotion, beauty, mouthstatus, eyegaze, skinstatus,
	 * nose_occlusion, chin_occlusion, face_occlusion.
	 * Default: "none" (no attributes detected).
	 */
	@JsonProperty("return_attributes")
	private String returnAttributes = "none";

	/**
	 * Minimum value for the beauty score range. Default: 0.
	 */
	@JsonProperty("beauty_score_min")
	private int beautyScoreMin = 0;

	/**
	 * Maximum value for the beauty score range. Default: 100.
	 */
	@JsonProperty("beauty_score_max")
	private int beautyScoreMax = 100;

}
