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
 * Response from the Face Detect API.
 * Contains the image identifier, detected face count, and an array of face objects.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFaceOperations#detectUrl(String, com.faceplusplus.spring.boot.req.FaceDetectOptions)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceDetectResponse extends FaceppResponse {

	/** Unique identifier of the detected image in the system. */
	@JsonProperty("image_id")
	private String imageId;

	/** Number of faces detected in the image. */
	@JsonProperty("face_num")
	private Integer faceNum;

	/** Array of detected face objects. Empty array if no faces are detected. */
	@JsonProperty("faces")
	private JSONArray faces;

}
