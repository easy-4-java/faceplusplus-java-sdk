package com.faceplusplus.spring.boot.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Base response class for all Face++ API responses.
 * Contains common fields shared across all API endpoints: status code,
 * time used, request ID, and error message.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceDetectResponse
 * @see FaceCompareResponse
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class FaceppResponse {

	/**
	 * HTTP-like status code. 200 indicates success; non-200 indicates failure.
	 */
	@JsonProperty("code")
	private int code;

	/**
	 * Time consumed by the entire request, in milliseconds.
	 * Always returned unless a 404 (API_NOT_FOUND) or 403 (AUTHORIZATION_ERROR) occurs.
	 */
	@JsonProperty("time_used")
	private int timeUsed;

	/**
	 * Unique request identifier string for tracing each request.
	 * Always returned unless a 404 or 403 error occurs.
	 */
	@JsonProperty("request_id")
	private String requestId;

	/**
	 * Error message returned when the request fails. Absent on success.
	 */
	@JsonProperty("error_message")
	private String errorMsg;

	/**
	 * Returns whether this response indicates a successful request (code == 200).
	 *
	 * @return {@code true} if the request was successful, {@code false} otherwise
	 */
	public boolean isSuccess() {
		return code == 200;
	}

}
