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
 * Response from the FaceSet Create API.
 * Contains the new FaceSet token, outer_id, face counts, and any failure details
 * for face tokens that could not be added during creation.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#createFaceset(com.faceplusplus.spring.boot.req.FacesetBo)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FacesetCreateResponse extends FaceppResponse {

	/** FaceSet token identifier. */
	@JsonProperty("faceset_token")
	private String facesetToken;

	/** User-defined FaceSet identifier. Empty if not defined. */
	@JsonProperty("outer_id")
	private String outerId;

	/** Number of face tokens successfully added in this operation. */
	@JsonProperty("face_added")
	private Integer faceAdded;

	/** Total number of face tokens in the FaceSet after this operation. */
	@JsonProperty("face_count")
	private Integer faceCount;

	/** List of face tokens that could not be added, with reasons. */
	@JsonProperty("failure_detail")
	private List<FailureFetail> detail;

	/**
	 * Details of a face token that failed to be added during FaceSet creation.
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
