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
 * Response from the Get FaceSet Detail API.
 * Contains full FaceSet metadata including token, display name, face count,
 * face token list, tags, outer_id, user data, and pagination cursor.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#getFacesetByToken(String, int)
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#getFacesetByOuterId(String, int)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FacesetDetailResponse extends FaceppResponse {

	/** FaceSet token identifier. */
	@JsonProperty("faceset_token")
	private String facesetToken;

	/** Display name of the FaceSet. Max 256 characters. */
	@JsonProperty("display_name")
	private String displayName;

	/** Total number of face tokens in the FaceSet. */
	@JsonProperty("face_count")
	private Integer faceCount;

	/** List of face tokens in the FaceSet. Empty if no faces exist. */
	@JsonProperty("face_tokens")
	private List<String> faceTokens;

	/** Comma-separated custom tags for grouping FaceSets. */
	@JsonProperty("tags")
	private String tags;

	/** User-defined FaceSet identifier. Empty if not defined. */
	@JsonProperty("outer_id")
	private String outerId;

	/** Custom user data. Max 16 KB. */
	@JsonProperty("user_data")
	private String userData;

	/**
	 * Pagination cursor for retrieving the next page of face tokens.
	 * Present only if more face tokens remain. Pass this value as the
	 * {@code start} parameter in the next request.
	 */
	@JsonProperty("next")
	private String next;

}
