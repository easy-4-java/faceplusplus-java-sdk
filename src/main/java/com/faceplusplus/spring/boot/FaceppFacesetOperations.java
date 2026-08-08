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
package com.faceplusplus.spring.boot;

import com.faceplusplus.spring.boot.req.FacesetBo;
import com.faceplusplus.spring.boot.req.FacesetDeleteBo;
import com.faceplusplus.spring.boot.req.FacesetUpdateBo;
import com.faceplusplus.spring.boot.resp.*;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Synchronous operations for FaceSet management, including create, delete, update,
 * list, and face token add/remove operations, as well as face information management.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppFacesetAsyncOperations
 * @see FaceppApiAddress
 */
public class FaceppFacesetOperations extends FaceppOperations {

	/**
	 * Constructs a new {@code FaceppFacesetOperations} bound to the given template.
	 *
	 * @param faceppTemplate the template providing access to SDK configuration and HTTP client
	 */
	public FaceppFacesetOperations(FaceppTemplate faceppTemplate) {
		super(faceppTemplate);
	}

	/**
	 * Creates a new FaceSet for storing face tokens.
	 *
	 * @param faceset the FaceSet creation parameters
	 * @return the creation response containing the new FaceSet token
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_CREATE
	 */
	public FacesetCreateResponse createFaceset(FacesetBo faceset) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_CREATE.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(faceset), Map.class))
				.build();
		FacesetCreateResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params, FacesetCreateResponse.class);
		return resp;
	}

	/**
	 * Deletes a FaceSet.
	 *
	 * @param faceset the FaceSet deletion parameters (token or outer_id)
	 * @return the deletion response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_DELETE
	 */
	public FacesetCreateResponse deleteFaceset(FacesetDeleteBo faceset) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_DELETE.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(faceset), Map.class))
				.build();
		FacesetCreateResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FacesetCreateResponse.class);
		return resp;
	}

	/**
	 * Updates the attributes of a FaceSet (display name, tags, user data, etc.).
	 *
	 * @param faceset the FaceSet update parameters
	 * @return the update response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_UPDATE
	 */
	public FacesetCreateResponse updateFaceset(FacesetUpdateBo faceset) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_UPDATE.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(faceset), Map.class))
				.build();
		FacesetCreateResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FacesetCreateResponse.class);
		return resp;
	}

	/**
	 * Retrieves the list of FaceSets under the current API key.
	 *
	 * @param lastSequence the starting sequence number for pagination
	 * @param tags         optional tags to filter FaceSets
	 * @return the list response containing FaceSet metadata
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_LIST
	 */
	public FacesetListResponse getFacesetList(int lastSequence, String... tags) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_LIST.getUrl();
		ImmutableMap.Builder builder = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("start", lastSequence);
		if(tags != null && tags.length > 0){
			if(tags.length == 1){
				builder.put("tags", tags[0]);
			} else {
				builder.put("tags", Stream.of(tags).collect(Collectors.joining(", ")));
			}
		}
		FacesetListResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, builder.build(),  FacesetListResponse.class);
		return resp;
	}

	/**
	 * Retrieves detailed information of a FaceSet identified by its token.
	 *
	 * @param facesetToken  the FaceSet identifier
	 * @param lastSequence  the starting sequence number for pagination
	 * @return the detail response containing FaceSet information and face tokens
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_LIST
	 */
	public FacesetDetailResponse getFacesetByToken(String facesetToken, int lastSequence) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_LIST.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("faceset_token", facesetToken)
				.put("start", lastSequence)
				.build();
		FacesetDetailResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FacesetDetailResponse.class);
		return resp;
	}

	/**
	 * Retrieves detailed information of a FaceSet identified by its outer_id.
	 *
	 * @param outerId       the user-defined FaceSet identifier
	 * @param lastSequence  the starting sequence number for pagination
	 * @return the detail response containing FaceSet information and face tokens
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACESET_DETAIL
	 */
	public FacesetDetailResponse getFacesetByOuterId(String outerId, int lastSequence) throws IOException {
		String reqUrl = FaceppApiAddress.FACESET_DETAIL.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("outer_id", outerId)
				.put("start", lastSequence)
				.build();
		FacesetDetailResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FacesetDetailResponse.class);
		return resp;
	}

	/**
	 * Adds face tokens to a FaceSet identified by its token.
	 *
	 * @param facesetToken the FaceSet identifier
	 * @param faceTokens   varargs of face tokens to add (max 5)
	 * @return the add response containing counts of added faces
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_ADD
	 */
	public FaceAddResponse addFaceWithToken(String facesetToken, String ... faceTokens) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_ADD.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("faceset_token", facesetToken)
				.put("face_tokens", faceTokens)
				.build();
		FaceAddResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceAddResponse.class);
		return resp;
	}

	/**
	 * Adds face tokens to a FaceSet identified by its outer_id.
	 *
	 * @param outerId    the user-defined FaceSet identifier
	 * @param faceTokens varargs of face tokens to add (max 5)
	 * @return the add response containing counts of added faces
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_ADD
	 */
	public FaceAddResponse addFaceWithOuterId(String outerId, String ... faceTokens) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_ADD.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("outer_id", outerId)
				.put("face_tokens", faceTokens)
				.build();
		FaceAddResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceAddResponse.class);
		return resp;
	}

	/**
	 * Removes face tokens from a FaceSet identified by its token.
	 *
	 * @param facesetToken the FaceSet identifier
	 * @param faceTokens   varargs of face tokens to remove
	 * @return the remove response containing counts of removed faces
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_REMOVE
	 */
	public FaceRemoveResponse removeFaceByToken(String facesetToken, String ... faceTokens) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_REMOVE.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("faceset_token", facesetToken)
				.put("face_tokens", faceTokens)
				.build();
		FaceRemoveResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceRemoveResponse.class);
		return resp;
	}

	/**
	 * Removes face tokens from a FaceSet identified by its outer_id.
	 *
	 * @param outerId    the user-defined FaceSet identifier
	 * @param faceTokens varargs of face tokens to remove
	 * @return the remove response containing counts of removed faces
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_REMOVE
	 */
	public FaceRemoveResponse removeFaceByOuterId(String outerId, String ... faceTokens) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_REMOVE.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("outer_id", outerId)
				.put("face_tokens", faceTokens)
				.build();
		FaceRemoveResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceRemoveResponse.class);
		return resp;
	}

	/**
	 * Sets a user-defined user_id for a face token, used to identify the person in search results.
	 *
	 * @param faceToken the face token identifier
	 * @param userId    the user-defined identifier (max 255 characters)
	 * @return the response containing the face token and user_id
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_SET_USERID
	 */
	public FaceSetUserIdResponse createFace(String faceToken, String userId) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_SET_USERID.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("user_id", userId)
				.put("face_token", faceToken)
				.build();
		FaceSetUserIdResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params, FaceSetUserIdResponse.class);
		return resp;
	}

	/**
	 * Retrieves detailed information about a face, including source image ID and associated FaceSets.
	 *
	 * @param faceToken the face token identifier
	 * @return the detail response containing face metadata
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_GET_DETAIL
	 */
	public FaceDetailResponse getFaceDetail(String faceToken) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_GET_DETAIL.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("face_token", faceToken)
				.build();
		FaceDetailResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceDetailResponse.class);
		return resp;
	}


}
