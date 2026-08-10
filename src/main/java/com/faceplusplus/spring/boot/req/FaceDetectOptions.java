package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Options for the Face Detect API.
 * Controls landmark detection, attribute analysis, face rectangle filtering,
 * and beauty score ranges.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#detectUrl(String, FaceDetectOptions)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
@Builder
public class FaceDetectOptions {

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
	 * Whether to analyze all detected faces (1) or only the 5 largest (0).
	 * Default: 0.
	 */
	@JsonProperty("calculate_all")
	private int calculateAll;

	/**
	 * Specifies a face rectangle for targeted detection.
	 * Format: "top,left,width,height" (e.g. "70,80,100,100").
	 * If null or empty, the entire image is scanned.
	 */
	@JsonProperty("face_rectangle")
	private String faceRectangle;

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
