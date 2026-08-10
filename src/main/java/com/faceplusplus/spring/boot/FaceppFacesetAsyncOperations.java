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

import com.faceplusplus.spring.boot.resp.FaceAddResponse;
import com.faceplusplus.spring.boot.resp.FaceRemoveResponse;
import com.faceplusplus.spring.boot.resp.FaceStatusResponse;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Async-capable operations for FaceSet management, including asynchronous
 * add/remove face operations and task status queries.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceppFacesetOperations
 * @see FaceppTemplate
 */
public class FaceppFacesetAsyncOperations extends FaceppFacesetOperations {

	/**
	 * Constructs a new {@code FaceppFacesetAsyncOperations} bound to the given template.
	 *
	 * @param faceppTemplate the template providing access to SDK configuration and HTTP client
	 */
	public FaceppFacesetAsyncOperations(FaceppTemplate faceppTemplate) {
		super(faceppTemplate);
	}

	/**
	 * Asynchronously adds face tokens to a FaceSet identified by its token.
	 *
	 * @param facesetToken the FaceSet identifier
	 * @param faceTokens   array of face tokens to add (max 5)
	 * @param consumer     callback to receive the response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_ADD_ASYNC
	 */
	public void asyncAddFaceWithToken(String facesetToken, String[] faceTokens, Consumer<FaceAddResponse> consumer) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_ADD_ASYNC.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("faceset_token", facesetToken)
				.put("face_tokens", faceTokens)
				.build();
		getFaceppOkHttp3Template().doAsyncRequest(reqUrl, FaceppOkHttp3Template.HttpMethod.POST, consumer, FaceAddResponse.class);
	}

	/**
	 * Asynchronously adds face tokens to a FaceSet identified by its outer_id.
	 *
	 * @param outerId    the user-defined FaceSet identifier
	 * @param faceTokens array of face tokens to add (max 5)
	 * @param consumer   callback to receive the response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_ADD_ASYNC
	 */
	public void asyncAddFaceWithOuterId(String outerId, String[] faceTokens, Consumer<FaceAddResponse> consumer) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_ADD_ASYNC.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("outer_id", outerId)
				.put("face_tokens", faceTokens)
				.build();
		getFaceppOkHttp3Template().doAsyncRequest(reqUrl, FaceppOkHttp3Template.HttpMethod.POST, consumer, FaceAddResponse.class);
	}

	/**
	 * Asynchronously removes face tokens from a FaceSet identified by its token.
	 *
	 * @param facesetToken the FaceSet identifier
	 * @param faceTokens   array of face tokens to remove
	 * @param consumer     callback to receive the response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_REMOVE_ASYNC
	 */
	public void asyncRemoveFaceByToken(String facesetToken, String[] faceTokens, Consumer<FaceRemoveResponse> consumer) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_REMOVE_ASYNC.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("faceset_token", facesetToken)
				.put("face_tokens", faceTokens)
				.build();
		getFaceppOkHttp3Template().doAsyncRequest(reqUrl, FaceppOkHttp3Template.HttpMethod.POST, consumer, FaceRemoveResponse.class);
	}

	/**
	 * Asynchronously removes face tokens from a FaceSet identified by its outer_id.
	 *
	 * @param outerId    the user-defined FaceSet identifier
	 * @param faceTokens array of face tokens to remove
	 * @param consumer   callback to receive the response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_REMOVE_ASYNC
	 */
	public void asyncRemoveFaceByOuterId(String outerId, String[] faceTokens, Consumer<FaceRemoveResponse> consumer) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_REMOVE_ASYNC.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("outer_id", outerId)
				.put("face_tokens", faceTokens)
				.build();
		getFaceppOkHttp3Template().doAsyncRequest(reqUrl, FaceppOkHttp3Template.HttpMethod.POST, consumer, FaceRemoveResponse.class);
	}

	/**
	 * Queries the status of an asynchronous add/remove face task.
	 *
	 * @param taskId the unique identifier of the async task
	 * @return the task status response
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_STATUS_ASYNC
	 */
	public FaceStatusResponse getFaceStatusByTaskId(String taskId) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_STATUS_ASYNC.getUrl();
		Map<String, Object> params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("task_id", taskId)
				.build();
		FaceStatusResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params,  FaceStatusResponse.class);
		return resp;
	}

}
