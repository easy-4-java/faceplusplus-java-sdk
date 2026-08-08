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

import java.util.List;

/**
 * Response from the Face Compare API.
 * Contains the comparison confidence score, reference thresholds,
 * image identifiers, and detected face arrays for both input images.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#compareUrl(String, String)
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#compareToken(String, String)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceCompareResponse extends FaceppResponse {

	/**
	 * Comparison confidence score in the range [0,100], with 3 decimal places.
	 * Higher values indicate a greater likelihood that the two faces belong to the same person.
	 * Not returned if no face is detected in the input images.
	 */
	@JsonProperty("confidence")
	private Float confidence;

	/**
	 * Reference confidence thresholds for different false acceptance rates:
	 * - 1e-3: threshold for 0.1% FAR
	 * - 1e-4: threshold for 0.01% FAR
	 * - 1e-5: threshold for 0.001% FAR
	 * Not returned if no face is detected in the input images.
	 */
	@JsonProperty("thresholds")
	private JSONObject thresholds;

	/** System identifier for the first input image. Not returned if no image was provided. */
	@JsonProperty("image_id1")
	private String imageId1;

	/** System identifier for the second input image. Not returned if no image was provided. */
	@JsonProperty("image_id2")
	private String imageId2;

	/** Array of faces detected in the first image. Not returned if no image was provided. */
	@JsonProperty("faces1")
	private JSONArray faces1;

	/** Array of faces detected in the second image. Not returned if no image was provided. */
	@JsonProperty("faces2")
	private JSONArray faces2;

}
