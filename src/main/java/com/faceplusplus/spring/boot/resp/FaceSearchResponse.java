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
 * Response from the Face Search API.
 * Contains matching face results, reference confidence thresholds,
 * the image identifier, and the detected face array.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#searchUrl(String, com.faceplusplus.spring.boot.req.FaceSearchOptions)
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#searchToken(String, com.faceplusplus.spring.boot.req.FaceSearchOptions)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceSearchResponse extends FaceppResponse {

	/**
	 * Array of search result objects, each containing a matched face token and confidence.
	 * Not returned if no face is detected in the input image.
	 */
	@JsonProperty("results")
	private JSONArray results;

	/**
	 * Reference confidence thresholds for different false acceptance rates:
	 * - 1e-3: threshold for 0.1% FAR
	 * - 1e-4: threshold for 0.01% FAR
	 * - 1e-5: threshold for 0.001% FAR
	 * Not returned if no face is detected in the input image.
	 */
	@JsonProperty("thresholds")
	private JSONObject thresholds;

	/** System identifier for the input image. Not returned if no image was provided. */
	@JsonProperty("image_id")
	private String imageId;

	/** Array of faces detected in the input image. Not returned if no image was provided. */
	@JsonProperty("faces")
	private JSONArray faces;

}
