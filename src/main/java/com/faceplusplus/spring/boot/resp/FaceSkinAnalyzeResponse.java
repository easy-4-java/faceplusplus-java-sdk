/*
 * Copyright (c) 2018, Loong Wan (https://github.com/loong10k).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.faceplusplus.spring.boot.resp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response from the Skin Analyze API.
 * Contains the face rectangle, skin analysis results, and any warning factors
 * that may have affected the analysis accuracy.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#skinAnalyzeUrl(String, com.faceplusplus.spring.boot.req.SkinAnalyzeType, com.faceplusplus.spring.boot.req.SkinAnalyzeOptions)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceSkinAnalyzeResponse extends FaceppResponse {

	/** Bounding rectangle of the detected face in the image. */
	@JsonProperty("face_rectangle")
	private FaceRectangle faceRectangle;

	/** Skin analysis result object containing all detected skin metrics. */
	@JsonProperty("result")
	private JSONObject result;

	/**
	 * Array of warning factors that may have affected analysis accuracy.
	 * Possible values: "improper_headpose" (roll/yaw/pitch outside [-45,45] range).
	 * Empty array if no warnings.
	 */
	@JsonProperty("warning")
	private JSONArray warning;

}
