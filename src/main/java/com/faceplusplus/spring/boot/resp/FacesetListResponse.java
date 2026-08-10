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
 * Response from the Get FaceSet List API.
 * Contains the list of FaceSets under the current API key, with pagination support.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see com.faceplusplus.spring.boot.FaceppFacesetOperations#getFacesetList(int, String...)
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude( JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FacesetListResponse extends FaceppResponse {

	/**
	 * Pagination cursor for retrieving the next page of FaceSets.
	 * Present only if more FaceSets remain. Pass this value as the
	 * {@code start} parameter in the next request.
	 */
	@JsonProperty("next")
	private String next;

	/** List of FaceSet summary objects. Empty if no FaceSets exist. */
	@JsonProperty("facesets")
	private List<FacesetFetail> facesets;

	/**
	 * Summary information for a single FaceSet in the list.
	 */
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class FacesetFetail {

		/** FaceSet token identifier. */
		@JsonProperty("faceset_token")
		private String facesetToken;

		/** User-defined FaceSet identifier. Empty if not defined. */
		@JsonProperty("outer_id")
		private String outerId;

		/** Display name of the FaceSet. Max 256 characters. */
		@JsonProperty("display_name")
		private String displayName;

		/** Comma-separated custom tags for grouping FaceSets. */
		@JsonProperty("tags")
		private String tags;

	}

}
