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

import com.faceplusplus.spring.boot.req.*;
import com.faceplusplus.spring.boot.resp.*;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Synchronous operations for the Face++ face recognition API group.
 * Provides methods for face detection, analysis, comparison, search, and skin analysis.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppFaceAsyncOperations
 * @see FaceppApiAddress
 */
public class FaceppFaceOperations extends FaceppOperations {

	/**
	 * Constructs a new {@code FaceppFaceOperations} bound to the given template.
	 *
	 * @param faceppTemplate the template providing access to SDK configuration and HTTP client
	 */
	public FaceppFaceOperations(FaceppTemplate faceppTemplate) {
		super(faceppTemplate);
	}

	/**
	 * Detects faces in an image specified by URL.
	 * Returns face tokens and optional attributes/landmarks for each detected face.
	 *
	 * @param imageUrl the URL of the image to analyze
	 * @param options  optional detection parameters (landmark, attributes, etc.)
	 * @return the detection response containing face tokens and metadata
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_DETECT
	 */
	public FaceDetectResponse detectUrl(String imageUrl, FaceDetectOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_DETECT.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_url", imageUrl)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceDetectResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params, FaceDetectResponse.class);
		return resp;
	}

	/**
	 * Detects faces in an image specified by Base64-encoded data.
	 *
	 * @param imageBase64 Base64-encoded binary image data
	 * @param options     optional detection parameters (landmark, attributes, etc.)
	 * @return the detection response containing face tokens and metadata
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_DETECT
	 */
	public FaceDetectResponse detectBase64(String imageBase64, FaceDetectOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_DETECT.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_base64", imageBase64)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceDetectResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params, FaceDetectResponse.class);
		return resp;
	}

	/**
	 * Detects faces in an image file uploaded as multipart/form-data.
	 *
	 * @param imageFile the image file to analyze
	 * @param options   optional detection parameters (landmark, attributes, etc.)
	 * @return the detection response containing face tokens and metadata
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_DETECT
	 */
	public FaceDetectResponse detectFile(File imageFile, FaceDetectOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_DETECT.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_file", imageFile)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceDetectResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceDetectResponse.class);
		return resp;
	}

	/**
	 * Analyzes facial attributes and landmarks for one or more face tokens.
	 * A single call supports up to 5 face tokens.
	 *
	 * @param faceTokens array of face token strings (max 5)
	 * @param options    optional analysis parameters
	 * @return the analysis response containing facial attributes and landmarks
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_ANALYZE
	 */
	public FaceAnalyzeResponse analyze(String[] faceTokens, FaceAnalyzeOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_ANALYZE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("face_tokens", faceTokens)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceAnalyzeResponse resp = getFaceppOkHttp3Template().post(reqUrl, null, params, FaceAnalyzeResponse.class);
		return resp;
	}

	/**
	 * Compares two faces specified by image URLs.
	 * Returns a confidence score indicating the likelihood that the two faces belong to the same person.
	 *
	 * @param imageUrl1 the URL of the first image
	 * @param imageUrl2 the URL of the second image
	 * @return the comparison response containing confidence and thresholds
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_COMPARE
	 */
	public FaceCompareResponse compareUrl(String imageUrl1, String imageUrl2) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_COMPARE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_url1", imageUrl1)
				.put("image_url2", imageUrl2)
				.build();
		FaceCompareResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceCompareResponse.class);
		return resp;
	}

	/**
	 * Compares two faces specified by their face tokens.
	 *
	 * @param faceToken1 the face token of the first face
	 * @param faceToken2 the face token of the second face
	 * @return the comparison response containing confidence and thresholds
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_COMPARE
	 */
	public FaceCompareResponse compareToken(String faceToken1, String faceToken2) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_COMPARE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("face_token1", faceToken1)
				.put("face_token2", faceToken2)
				.build();
		FaceCompareResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceCompareResponse.class);
		return resp;
	}

	/**
	 * Compares two faces specified by Base64-encoded image data.
	 *
	 * @param imageBase64_1 Base64-encoded binary data of the first image
	 * @param imageBase64_2 Base64-encoded binary data of the second image
	 * @return the comparison response containing confidence and thresholds
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_COMPARE
	 */
	public FaceCompareResponse compareBase64(String imageBase64_1, String imageBase64_2) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_DETECT.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_base64_1", imageBase64_1)
				.put("image_base64_2", imageBase64_2)
				.build();
		FaceCompareResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceCompareResponse.class);
		return resp;
	}

	/**
	 * Compares two faces specified by image files uploaded as multipart/form-data.
	 *
	 * @param imageFile1 the first image file
	 * @param imageFile2 the second image file
	 * @return the comparison response containing confidence and thresholds
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_COMPARE
	 */
	public FaceCompareResponse compareFile(File imageFile1, File imageFile2) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_COMPARE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_file1", imageFile1)
				.put("image_file2", imageFile2)
				.build();
		FaceCompareResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceCompareResponse.class);
		return resp;
	}

	/**
	 * Searches for similar faces in a FaceSet using an image URL.
	 *
	 * @param imageUrl the URL of the image containing the target face
	 * @param options  optional search parameters
	 * @return the search response containing matching face results
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_SEARCH
	 */
	public FaceSearchResponse searchUrl(String imageUrl, FaceSearchOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_COMPARE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_url", imageUrl)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSearchResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSearchResponse.class);
		return resp;
	}

	/**
	 * Searches for similar faces in a FaceSet using a face token.
	 *
	 * @param faceToken the face token of the target face
	 * @param options   optional search parameters
	 * @return the search response containing matching face results
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_SEARCH
	 */
	public FaceSearchResponse searchToken(String faceToken, FaceSearchOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_COMPARE.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("face_token", faceToken)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSearchResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSearchResponse.class);
		return resp;
	}

	/**
	 * Searches for similar faces in a FaceSet using Base64-encoded image data.
	 *
	 * @param imageBase64 Base64-encoded binary image data
	 * @param options     optional search parameters
	 * @return the search response containing matching face results
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_SEARCH
	 */
	public FaceSearchResponse searchBase64(String imageBase64, FaceSearchOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_DETECT.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_base64", imageBase64)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSearchResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSearchResponse.class);
		return resp;
	}

	/**
	 * Searches for similar faces in a FaceSet using an image file.
	 *
	 * @param imageFile the image file containing the target face
	 * @param options   optional search parameters
	 * @return the search response containing matching face results
	 * @throws IOException if the HTTP request fails
	 * @see FaceppApiAddress#FACE_SEARCH
	 */
	public FaceSearchResponse searchFile(File imageFile, FaceSearchOptions options) throws IOException {
		String reqUrl = FaceppApiAddress.FACE_SEARCH.getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_file", imageFile)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSearchResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSearchResponse.class);
		return resp;
	}

	/**
	 * Performs skin analysis on a face in an image specified by URL.
	 *
	 * @param imageUrl the URL of the image containing the face
	 * @param type     the skin analysis type (BASIC, ADVANCED, or PRO)
	 * @param options  optional skin analysis parameters
	 * @return the skin analysis response
	 * @throws IOException if the HTTP request fails
	 * @see SkinAnalyzeType
	 */
	public FaceSkinAnalyzeResponse skinAnalyzeUrl(String imageUrl, SkinAnalyzeType type, SkinAnalyzeOptions options) throws IOException {
		String reqUrl = type.getApiAddress().getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_url", imageUrl)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSkinAnalyzeResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSkinAnalyzeResponse.class);
		return resp;
	}

	/**
	 * Performs skin analysis on a face in an image specified by Base64-encoded data.
	 *
	 * @param imageBase64 Base64-encoded binary image data
	 * @param type        the skin analysis type (BASIC, ADVANCED, or PRO)
	 * @param options     optional skin analysis parameters
	 * @return the skin analysis response
	 * @throws IOException if the HTTP request fails
	 * @see SkinAnalyzeType
	 */
	public FaceSkinAnalyzeResponse skinAnalyzeBase64(String imageBase64, SkinAnalyzeType type, SkinAnalyzeOptions options) throws IOException {
		String reqUrl = type.getApiAddress().getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_base64", imageBase64)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSkinAnalyzeResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSkinAnalyzeResponse.class);
		return resp;
	}

	/**
	 * Performs skin analysis on a face in an image file.
	 *
	 * @param imageFile the image file containing the face
	 * @param type      the skin analysis type (BASIC, ADVANCED, or PRO)
	 * @param options   optional skin analysis parameters
	 * @return the skin analysis response
	 * @throws IOException if the HTTP request fails
	 * @see SkinAnalyzeType
	 */
	public FaceSkinAnalyzeResponse skinAnalyzeFile(File imageFile, SkinAnalyzeType type, SkinAnalyzeOptions options) throws IOException {
		String reqUrl = type.getApiAddress().getUrl();
		Map params = new ImmutableMap.Builder<String, Object>()
				.put("api_key", getFaceppProperties().getAppId())
				.put("api_secret", getFaceppProperties().getAppCertificate())
				.put("image_file", imageFile)
				.putAll(getObjectMapper().readValue(getObjectMapper().writeValueAsString(options), Map.class))
				.build();
		FaceSkinAnalyzeResponse resp = getFaceppOkHttp3Template().doPartRequest(reqUrl, params, FaceSkinAnalyzeResponse.class);
		return resp;
	}

}
