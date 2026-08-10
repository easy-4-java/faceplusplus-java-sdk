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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response from the Get Face Detail API.
 * Contains the source image ID, face token, user ID, face rectangle,
 * and the list of FaceSets containing this face.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#getFaceDetail(String)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceDetailResponse extends FaceppResponse {

	/** System identifier of the source image containing this face. */
	@JsonProperty("image_id")
	private String imageId;

	/** The face token identifier. */
	@JsonProperty("face_token")
	private String faceToken;

	/** User-defined identifier for this face. Empty if not set. */
	@JsonProperty("user_id")
	private String userId;

	/** Bounding rectangle of the face in the source image. */
	@JsonProperty("face_rectangle")
	private FaceRectangle faceRectangle;

	/** List of FaceSets that contain this face token. */
	@JsonProperty("facesets")
	private List<FaceSetFetail> facesets;


}
