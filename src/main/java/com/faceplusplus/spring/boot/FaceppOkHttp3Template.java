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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faceplusplus.spring.boot.resp.FaceppResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * OkHttp3-based HTTP request template for the Face++ SDK.
 * Supports synchronous and asynchronous requests, JSON and multipart form data,
 * and automatic response deserialization.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppProperties
 * @see FaceppResponse
 */
@Slf4j
public class FaceppOkHttp3Template  {

	/** Content type for JSON requests. */
	public final static String APPLICATION_JSON_VALUE = "application/json";

	/** Content type for JSON requests with UTF-8 encoding. */
	public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

	/** Content type for binary stream requests. */
	public final static String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";

	/** Parsed media type for JSON. */
	public final static MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_VALUE);

	/** Parsed media type for JSON with UTF-8 encoding. */
	public final static MediaType APPLICATION_JSON_UTF8 = MediaType.parse(APPLICATION_JSON_UTF8_VALUE);

	/** Parsed media type for binary stream. */
	public final static MediaType APPLICATION_OCTET_STREAM = MediaType.parse(APPLICATION_OCTET_STREAM_VALUE);

	protected OkHttpClient okhttp3Client;
	protected ObjectMapper objectMapper;
	protected FaceppProperties agoraProperties;

	/**
	 * Constructs a new {@code FaceppOkHttp3Template} with the given HTTP client, mapper, and properties.
	 *
	 * @param okhttp3Client  the OkHttp3 client instance
	 * @param objectMapper   the Jackson ObjectMapper for JSON serialization
	 * @param agoraProperties the Face++ configuration properties
	 */
	public FaceppOkHttp3Template(OkHttpClient okhttp3Client, ObjectMapper objectMapper, FaceppProperties agoraProperties) {
		this.okhttp3Client = okhttp3Client;
		this.objectMapper = objectMapper;
		this.agoraProperties = agoraProperties;
	}

	private void init() {
		if (okhttp3Client == null) {
			okhttp3Client = new OkHttpClient().newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS)
					.pingInterval(1, TimeUnit.MILLISECONDS).readTimeout(3000, TimeUnit.MILLISECONDS)
					.retryOnConnectionFailure(true)
					.writeTimeout(3, TimeUnit.SECONDS)
					.build();
		}
	}

	/**
	 * Sends a POST request without parameters.
	 *
	 * @param url     the request URL
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T post(String url, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.POST, null, null, null, rtClass);
	}

	/**
	 * Sends a POST request with query parameters.
	 *
	 * @param url     the request URL
	 * @param params  the query parameters
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T post(String url, Map<String, Object> params, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.POST, null, params, null, rtClass);
	}

	/**
	 * Sends a POST request with headers and query parameters.
	 *
	 * @param url     the request URL
	 * @param headers the request headers
	 * @param params  the query parameters
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T post(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.POST, headers, params, null, rtClass);
	}

	/**
	 * Sends a POST request with headers, query parameters, and a JSON body.
	 *
	 * @param url         the request URL
	 * @param headers     the request headers
	 * @param params      the query parameters
	 * @param bodyContent the JSON body content
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T post(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, Object> bodyContent, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.POST, headers, params, bodyContent, rtClass);
	}

	/**
	 * Sends a GET request without parameters.
	 *
	 * @param url     the request URL
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T get(String url, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.GET, null, null, null, rtClass);
	}

	/**
	 * Sends a GET request with query parameters.
	 *
	 * @param url     the request URL
	 * @param params  the query parameters
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T get(String url, Map<String, Object> params, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.GET, null, params, null, rtClass);
	}

	/**
	 * Sends a GET request with headers and query parameters.
	 *
	 * @param url     the request URL
	 * @param headers the request headers
	 * @param params  the query parameters
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T get(String url, Map<String, Object> headers, Map<String, Object> params, Class<T> rtClass) throws IOException {
		return this.doRequest(url, HttpMethod.GET, headers, params, null, rtClass);
	}

	/**
	 * Sends an HTTP request with full parameter control and deserializes the response.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T doRequest(
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Map<String, Object> bodyContent,
			Class<T> rtClass) throws IOException {
		long startTime = System.currentTimeMillis();
		HttpUrl httpUrl = this.getHttpUrl(url, queryParams);
		return this.doRequest(startTime, httpUrl, method, headers, bodyContent, rtClass);
	}

	/**
	 * Sends an HTTP request with a pre-built {@link HttpUrl} and deserializes the response.
	 *
	 * @param startTime   the request start timestamp for logging
	 * @param httpUrl     the pre-built HTTP URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T doRequest(
			long startTime,
			HttpUrl httpUrl,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> bodyContent,
			Class<T> rtClass) throws IOException {
		Response response = this.doRequest(startTime, httpUrl, method, headers, bodyContent);
		T res = null;
		try {
			if (response.isSuccessful()) {
				String body = response.body().string();
				res = this.readValue(body, rtClass);
				res.setCode(response.code());
			} else {
				res = safeInstantiate(rtClass);
				res.setCode(response.code());
			}
		} catch (Exception e) {
			log.error("Face++ >> Request Error : {}, use time : {}", e.getMessage(), System.currentTimeMillis() - startTime);
			res = safeInstantiate(rtClass);
		}
		return res;
	}

	/**
	 * Sends a raw HTTP request and returns the raw OkHttp response.
	 *
	 * @param url    the request URL
	 * @param method the HTTP method
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			String url,
			HttpMethod method) throws IOException {
		return this.doRequest(url, method, null);
	}

	/**
	 * Sends a raw HTTP request with query parameters.
	 *
	 * @param url        the request URL
	 * @param method     the HTTP method
	 * @param queryParams the query parameters (may be null)
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			String url,
			HttpMethod method,
			Map<String, Object> queryParams) throws IOException {
		return this.doRequest(url, method, null, queryParams);
	}

	/**
	 * Sends a raw HTTP request with headers and query parameters.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams) throws IOException {
		return this.doRequest(url, method, headers, queryParams, null);
	}

	/**
	 * Sends a raw HTTP request with headers, query parameters, and body content.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Map<String, Object> bodyContent) throws IOException {
		long startTime = System.currentTimeMillis();
		return this.doRequest(startTime, url, method, headers, queryParams, bodyContent);
	}

	/**
	 * Sends a raw HTTP request with explicit start time for logging.
	 *
	 * @param startTime   the request start timestamp
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			long startTime,
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Map<String, Object> bodyContent) throws IOException {
		HttpUrl httpUrl = this.getHttpUrl(url, queryParams);
		return this.doRequest(startTime, httpUrl, method, headers, bodyContent);
	}

	/**
	 * Sends a multipart form-data request and deserializes the response.
	 * Used for file upload endpoints.
	 *
	 * @param httpUrl the request URL
	 * @param params  the form parameters (values may be {@link File} instances)
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @return the deserialized response
	 * @throws IOException if the request fails
	 */
	public <T extends FaceppResponse> T doPartRequest(
			String httpUrl,
			Map<String, Object> params,
			Class<T> rtClass) throws IOException {

		long startTime = System.currentTimeMillis();
		Response response = this.doPartRequest(startTime, httpUrl, params);
		T res = null;
		try {
			if (response.isSuccessful()) {
				String body = response.body().string();
				res = this.readValue(body, rtClass);
				res.setCode(response.code());
			} else {
				res = safeInstantiate(rtClass);
				res.setCode(response.code());
			}
		} catch (Exception e) {
			log.error("Face++ >> Request Error : {}, use time : {}", e.getMessage(), System.currentTimeMillis() - startTime);
			res = safeInstantiate(rtClass);
		}
		return res;
	}

	/**
	 * Sends a multipart form-data request and returns the raw response.
	 *
	 * @param startTime the request start timestamp
	 * @param httpUrl   the request URL
	 * @param params    the form parameters (values may be {@link File} instances)
	 * @return the raw OkHttp response, or null if an error occurs
	 * @throws IOException if the request fails
	 */
	public Response doPartRequest(
			long startTime,
			String httpUrl,
			Map<String, Object> params) throws IOException {

		Request.Builder builder = new Request.Builder().url(httpUrl);
		MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		for (Map.Entry<String, Object> entry : params.entrySet()){
			Object val = entry.getValue();
			if(val instanceof File){
				File file = (File) val;
				RequestBody fileBody = RequestBody.create(FaceppOkHttp3Template.APPLICATION_OCTET_STREAM, file);
				bodyBuilder.addFormDataPart(entry.getKey(), file.getName(), fileBody );
			} else {
				bodyBuilder.addFormDataPart(entry.getKey(), Objects.toString(entry.getValue()) );
			}
		}
		try {
			Response response = okhttp3Client.newCall(builder.post(bodyBuilder.build()).build()).execute();
			if (response.isSuccessful()) {
				log.info("Face++ >> Request Success : code : {}, use time : {} ", response.code(), System.currentTimeMillis() - startTime);
			} else {
				log.error("Face++ >> Request Failure : code : {}, message : {}, use time : {} ", response.code(), response.message(), System.currentTimeMillis() - startTime);
			}
			return response;
		} catch (IOException e) {
			log.error("OkHttp3 Request Error : {}, use time : {}", e.getMessage(), System.currentTimeMillis() - startTime);
		}
		return null;
	}

	/**
	 * Sends a raw HTTP request with a pre-built {@link HttpUrl} and returns the raw response.
	 *
	 * @param startTime   the request start timestamp
	 * @param httpUrl     the pre-built HTTP URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @return the raw OkHttp response
	 * @throws IOException if the request fails
	 */
	public Response doRequest(
			long startTime,
			HttpUrl httpUrl,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> bodyContent) throws IOException {
		try {
			Request.Builder builder = this.createRequestBuilder(httpUrl, method, headers, bodyContent);
			Response response = okhttp3Client.newCall(builder.build()).execute();
			if (response.isSuccessful()) {
				log.info("Face++ >> Request Success : code : {}, use time : {} ", response.code(), System.currentTimeMillis() - startTime);
			} else {
				log.error("Face++ >> Request Failure : code : {}, message : {}, use time : {} ", response.code(), response.message(), System.currentTimeMillis() - startTime);
			}
			return response;
		} catch (IOException e) {
			log.error("OkHttp3 Request Error : {}, use time : {}", e.getMessage(), System.currentTimeMillis() - startTime);
			throw e;
		}
	}

	/**
	 * Sends an asynchronous request with a success callback.
	 *
	 * @param url     the request URL
	 * @param method  the HTTP method
	 * @param success callback to receive the deserialized response
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			String url,
			HttpMethod method,
			Consumer<T> success,
			Class<T> rtClass) throws IOException {
		this.doAsyncRequest(url, method, success, null, rtClass);
	}

	/**
	 * Sends an asynchronous request with success and failure callbacks.
	 *
	 * @param url     the request URL
	 * @param method  the HTTP method
	 * @param success callback to receive the deserialized response
	 * @param failure callback to handle errors (may be null)
	 * @param rtClass the expected response type
	 * @param <T>     the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			String url,
			HttpMethod method,
			Consumer<T> success,
			BiFunction<Call, IOException, Boolean> failure,
			Class<T> rtClass) throws IOException {
		this.doAsyncRequest(url, method, null, success, failure, rtClass);
	}

	/**
	 * Sends an asynchronous request with query parameters and callbacks.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param queryParams the query parameters (may be null)
	 * @param success     callback to receive the deserialized response
	 * @param failure     callback to handle errors (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			String url,
			HttpMethod method,
			Map<String, Object> queryParams,
			Consumer<T> success,
			BiFunction<Call, IOException, Boolean> failure,
			Class<T> rtClass) throws IOException {
		this.doAsyncRequest(url, method, null, queryParams, success, failure, rtClass);
	}

	/**
	 * Sends an asynchronous request with headers, query parameters, and callbacks.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param success     callback to receive the deserialized response
	 * @param failure     callback to handle errors (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Consumer<T> success,
			BiFunction<Call, IOException, Boolean> failure,
			Class<T> rtClass) throws IOException {
		this.doAsyncRequest(url, method, headers, queryParams, null, success, failure, rtClass);
	}

	/**
	 * Sends an asynchronous request with full parameter control.
	 *
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param success     callback to receive the deserialized response
	 * @param failure     callback to handle errors (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Map<String, Object> bodyContent,
			Consumer<T> success,
			BiFunction<Call, IOException, Boolean> failure,
			Class<T> rtClass) throws IOException {
		long startTime = System.currentTimeMillis();
		HttpUrl httpUrl = this.getHttpUrl(url, queryParams);
		this.doAsyncRequest(startTime, httpUrl, method, headers, bodyContent, success, failure, rtClass);
	}

	/**
	 * Sends an asynchronous request with a pre-built {@link HttpUrl} and typed callbacks.
	 *
	 * @param startTime   the request start timestamp
	 * @param httpUrl     the pre-built HTTP URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param success     callback to receive the deserialized response
	 * @param failure     callback to handle errors (may be null)
	 * @param rtClass     the expected response type
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			long startTime,
			HttpUrl httpUrl,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> bodyContent,
			Consumer<T> success,
			BiFunction<Call, IOException, Boolean> failure,
			Class<T> rtClass) throws IOException {
		this.doAsyncRequest(startTime, httpUrl, method, headers, bodyContent, (call, response) -> {
			T res;
			try {
				if (response.isSuccessful()) {
					String body = response.body().string();
					res = this.readValue(body, rtClass);
					res.setCode(response.code());
				} else {
					res = safeInstantiate(rtClass);
					res.setCode(response.code());
				}
			} catch (Exception e) {
				log.error("Face++ >> Async Request Error : {}, use time : {}", e.getMessage(), System.currentTimeMillis() - startTime);
				res = safeInstantiate(rtClass);
			}
			success.accept(res);
			return res;
		}, failure);
	}

	/**
	 * Sends an asynchronous request with raw response callbacks.
	 *
	 * @param startTime   the request start timestamp
	 * @param url         the request URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param queryParams the query parameters (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param success     callback to process the raw response
	 * @param failure     callback to handle errors (may be null)
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			long startTime,
			String url,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> queryParams,
			Map<String, Object> bodyContent,
			BiFunction<Call, Response, T> success,
			BiFunction<Call, IOException, Boolean> failure) throws IOException {
		HttpUrl httpUrl = this.getHttpUrl(url, queryParams);
		this.doAsyncRequest(startTime, httpUrl, method, headers, bodyContent, success, failure);
	}

	/**
	 * Sends an asynchronous request with a pre-built {@link HttpUrl} and raw response callbacks.
	 *
	 * @param startTime   the request start timestamp
	 * @param httpUrl     the pre-built HTTP URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @param success     callback to process the raw response
	 * @param failure     callback to handle errors (may be null)
	 * @param <T>         the response type extending {@link FaceppResponse}
	 * @throws IOException if the request setup fails
	 */
	public <T extends FaceppResponse> void doAsyncRequest(
			long startTime,
			HttpUrl httpUrl,
			HttpMethod method,
			Map<String, Object> headers,
			Map<String, Object> bodyContent,
			BiFunction<Call, Response, T> success,
			BiFunction<Call, IOException, Boolean> failure) throws IOException {
		Request.Builder builder = this.createRequestBuilder(httpUrl, method, headers, bodyContent);
		okhttp3Client.newCall(builder.build()).enqueue(new Callback() {

			public void onFailure(Call call, IOException e) {
				log.error("Face++ >> Async Request Failure : {}, use time : {} ", e.getMessage(), System.currentTimeMillis() - startTime);
				if (Objects.nonNull(failure)) {
					failure.apply(call, e);
				}
			}

			public void onResponse(Call call, Response response) {
				if (response.isSuccessful()) {
					log.info("Face++ >> Async Request Success : code : {}, use time : {} ", response.code(), System.currentTimeMillis() - startTime);
				} else {
					log.error("Face++ >> Async Request Failure : code : {}, message : {}, use time : {} ", response.code(), response.message(), System.currentTimeMillis() - startTime);
				}
				if (Objects.nonNull(success)) {
					success.apply(call, response);
				}
			}

		});
	}

	/**
	 * Builds an {@link HttpUrl} from a base URL string and optional query parameters.
	 *
	 * @param httpUrl the base URL string
	 * @param params  the query parameters to append (may be null)
	 * @return the constructed {@link HttpUrl}
	 */
	public HttpUrl getHttpUrl(String httpUrl, Map<String, Object> params) {
		log.info("Face++ >> Request Url : {}", httpUrl);
		HttpUrl.Builder urlBuilder = HttpUrl.parse(httpUrl).newBuilder();
		if ((params == null || params.isEmpty())) {
			return urlBuilder.build();
		}
		if (!(params == null || params.isEmpty())) {
			log.info("Face++ >> Request Params : {}", params);
			Iterator<Entry<String, Object>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				urlBuilder.addQueryParameter(entry.getKey(), Objects.isNull(entry.getValue()) ? "" : entry.getValue().toString());
			}
		}
		return urlBuilder.build();
	}

	/**
	 * Creates a {@link Request.Builder} with the given URL, method, headers, and optional body content.
	 *
	 * @param httpUrl     the target HTTP URL
	 * @param method      the HTTP method
	 * @param headers     the request headers (may be null)
	 * @param bodyContent the JSON body content (may be null)
	 * @return the configured request builder
	 * @throws IOException if JSON serialization of body content fails
	 */
	public Request.Builder createRequestBuilder(HttpUrl httpUrl,
												  HttpMethod method,
												  Map<String, Object> headers,
												  Map<String, Object> bodyContent) throws IOException{
		log.info("Face++ >> Request Query Url : {} , Method : {}", httpUrl.query() , method.getName());
		Request.Builder builder = new Request.Builder().url(httpUrl);
		if(Objects.nonNull(headers)) {
			log.info("Face++ >> Request Headers : {}", headers);
			for (Entry<String, Object> entry : headers.entrySet()) {
				builder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		if(Objects.nonNull(bodyContent)) {
			String bodyStr = objectMapper.writeValueAsString(bodyContent);
			log.info("Face++ >> Request Body : {}", bodyStr);
			builder = method.apply(builder, bodyStr);
		} else {
			builder = method.apply(builder);
		}
		return builder;
	}

	/**
	 * Deserializes a JSON string into the specified type using FastJSON.
	 *
	 * @param json the JSON string to deserialize
	 * @param cls  the target class type
	 * @param <T>  the response type extending {@link FaceppResponse}
	 * @return the deserialized object, or a default instance if parsing fails
	 */
	public <T extends FaceppResponse> T readValue(String json, Class<T> cls) {
		try {
			return JSONObject.parseObject(json, cls);
		} catch (Exception e) {
			log.error(e.getMessage());
			return safeInstantiate(cls);
		}
	}

	/**
	 * Enumeration of HTTP methods with their request builder application logic.
	 */
	public static enum HttpMethod {

		/** GET request method. */
		GET("GET", (builder, bodyStr)->{
			return builder.get();
		}),

		/** HEAD request method. */
		HEAD("HEAD", (builder, bodyStr)->{
			return builder.head();
		}),

		/** POST request method with JSON body. */
		POST("POST", (builder, bodyStr)->{
			return builder.post(RequestBody.create(APPLICATION_JSON_UTF8, bodyStr));
		}),

		/** PUT request method with JSON body. */
		PUT("PUT", (builder, bodyStr)->{
			return builder.put(RequestBody.create(APPLICATION_JSON_UTF8, bodyStr));
		}),

		/** PATCH request method with JSON body. */
		PATCH("PATCH", (builder, bodyStr)->{
			return builder.patch(RequestBody.create(APPLICATION_JSON_UTF8, bodyStr));
		}),

		/** DELETE request method, optionally with a JSON body. */
		DELETE("DELETE", (builder, bodyStr)->{
			return StringUtils.isNotBlank(bodyStr) ? builder.delete(RequestBody.create(APPLICATION_JSON_UTF8, bodyStr)) : builder.delete();
		}),

		/** OPTIONS request method. */
		OPTIONS("OPTIONS", (builder, bodyStr)->{
			return builder;
		}),

		/** TRACE request method. */
		TRACE("TRACE", (builder, bodyStr)->{
			return builder;
		});

		private String name;
		private BiFunction<Request.Builder, String, Request.Builder> function;

		HttpMethod(String name, BiFunction<Request.Builder, String, Request.Builder> function) {
			this.name = name;
			this.function = function;
		}

		/**
		 * Returns the HTTP method name.
		 *
		 * @return the method name string
		 */
		public String getName() {
			return name;
		}

		/**
		 * Applies this HTTP method to the given request builder with a body string.
		 *
		 * @param builder the request builder
		 * @param bodyStr the body content string
		 * @return the modified request builder
		 */
		public Request.Builder apply(Request.Builder builder, String bodyStr){
			return function.apply(builder, bodyStr);
		}

		/**
		 * Applies this HTTP method to the given request builder without a body.
		 *
		 * @param builder the request builder
		 * @return the modified request builder
		 */
		public Request.Builder apply(Request.Builder builder){
			return function.apply(builder, null);
		}

		/**
		 * Looks up an HTTP method by its integer ordinal.
		 *
		 * @param name the ordinal number
		 * @return the matching {@link HttpMethod}, or null if not found
		 */
		public static HttpMethod getByName(int name) {
			for (HttpMethod type : HttpMethod.values()) {
				if (type.getName().equals(name)) {
					return type;
				}
			}
			return null;
		}

	}

	/**
	 * Returns the Jackson ObjectMapper used for JSON serialization.
	 *
	 * @return the {@link ObjectMapper} instance
	 */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

    private <T> T safeInstantiate(Class<T> cls) {
        try { return safeInstantiate(cls); } catch (Exception e) { return null; }
    }
}
