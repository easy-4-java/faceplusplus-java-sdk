package com.faceplusplus.spring.boot.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Options for the Skin Analyze API.
 * Controls face quality enforcement, confidence return, and result map types.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#skinAnalyzeUrl(String, SkinAnalyzeType, SkinAnalyzeOptions)
 */
@JsonInclude( JsonInclude.Include.NON_NULL)
@Data
@Builder
public class SkinAnalyzeOptions {

	/**
	 * Whether to enforce face quality control.
	 * 1 = enforce (returns INVALID_FACE_SIZE or INVALID_FACE_QUALITY on failure),
	 * 0 = no enforcement. Default: 0.
	 */
	@JsonProperty("face_quality_control")
	private int faceQualityControl;

	/**
	 * Whether to return region confidence for acne, closed comedones, spots, and moles.
	 * 1 = return confidence, 0 = do not return. Default: 0.
	 */
	@JsonProperty("return_rect_confidence")
	private int returnRectConfidence;

	/**
	 * Comma-separated list of result map types to return.
	 * Supported values: red_area, brown_area, texture_enhanced_pores,
	 * texture_enhanced_blackheads, texture_enhanced_oily_area, texture_enhanced_lines.
	 * Default: empty string (no maps returned).
	 */
	@JsonProperty("return_maps")
	private String returnMaps;

}
