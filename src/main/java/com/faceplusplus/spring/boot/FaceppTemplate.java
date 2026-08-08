package com.faceplusplus.spring.boot;

import lombok.extern.slf4j.Slf4j;

/**
 * Main entry point for the Face++ SDK.
 * Provides access to face detection/analysis operations and FaceSet management operations.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppFaceAsyncOperations
 * @see FaceppFacesetAsyncOperations
 */
@Slf4j
public class FaceppTemplate {

	private FaceppOkHttp3Template faceppOkHttp3Template;
	private FaceppProperties faceppProperties;

	private final FaceppFacesetAsyncOperations facesetOps = new FaceppFacesetAsyncOperations(this);
	private final FaceppFaceAsyncOperations faceDetectOps = new FaceppFaceAsyncOperations(this);

	/**
	 * Constructs a new {@code FaceppTemplate} with the given HTTP template and properties.
	 *
	 * @param faceppOkHttp3Template the OkHttp3 template for HTTP communication
	 * @param faceppProperties the Face++ configuration properties
	 */
	public FaceppTemplate(FaceppOkHttp3Template faceppOkHttp3Template, FaceppProperties faceppProperties) {
		this.faceppOkHttp3Template = faceppOkHttp3Template;
		this.faceppProperties = faceppProperties;
	}

	/**
	 * Returns the operations interface for FaceSet management (create, delete, update, add/remove faces).
	 *
	 * @return the {@link FaceppFacesetAsyncOperations} instance
	 */
	public FaceppFacesetAsyncOperations opsForFaceset() {
		return facesetOps;
	}

	/**
	 * Returns the operations interface for face detection, analysis, comparison, search, and skin analysis.
	 *
	 * @return the {@link FaceppFaceAsyncOperations} instance
	 */
	public FaceppFaceAsyncOperations opsForFaceDetect() {
		return faceDetectOps;
	}

	/**
	 * Returns the Face++ configuration properties.
	 *
	 * @return the {@link FaceppProperties} instance
	 */
	public FaceppProperties getFaceppProperties() {
		return faceppProperties;
	}

	/**
	 * Returns the OkHttp3 template used for HTTP communication.
	 *
	 * @return the {@link FaceppOkHttp3Template} instance
	 */
	public FaceppOkHttp3Template getFaceppOkHttp3Template() {
		return faceppOkHttp3Template;
	}


}
