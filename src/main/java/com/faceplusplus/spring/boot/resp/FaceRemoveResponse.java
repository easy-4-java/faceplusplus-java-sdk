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
 * Response from the FaceSet Remove Face API.
 * Contains the FaceSet token, outer_id, the number of faces removed,
 * total face count, and any failure details.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#removeFaceByToken(String, String...)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceRemoveResponse extends FaceppResponse {

	/** FaceSet token identifier. */
	@JsonProperty("faceset_token")
	private String facesetToken;

	/** User-defined FaceSet identifier. Empty if not defined. */
	@JsonProperty("outer_id")
	private String outerId;

	/** Number of face tokens successfully removed in this operation. */
	@JsonProperty("face_removed")
	private Integer faceRemoved;

	/** Total number of face tokens in the FaceSet after this operation. */
	@JsonProperty("face_count")
	private Integer faceCount;

	/** List of face tokens that could not be removed, with reasons. */
	@JsonProperty("failure_detail")
	private List<FaceAddResponse.FailureFetail> detail;

	/**
	 * Details of a face token that failed to be removed from the FaceSet.
	 */
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class FailureFetail {

		/** The face token that failed. */
		@JsonProperty("face_token")
		private String token;

		/** Reason for failure: INVALID_FACE_TOKEN or QUOTA_EXCEEDED. */
		@JsonProperty("reason")
		private String reason;

	}

}
