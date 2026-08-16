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

import tools.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class for all Face++ API operation classes.
 * Provides common access to {@link FaceppTemplate}, {@link FaceppProperties},
 * {@link FaceppOkHttp3Template}, and the shared {@link ObjectMapper}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceppTemplate
 * @see FaceppFaceOperations
 * @see FaceppFacesetOperations
 */
@Slf4j
public abstract class FaceppOperations {

	protected FaceppTemplate faceppTemplate;

	/**
	 * Constructs a new {@code FaceppOperations} bound to the given template.
	 *
	 * @param faceppTemplate the template providing access to SDK configuration and HTTP client
	 */
	public FaceppOperations(FaceppTemplate faceppTemplate) {
		this.faceppTemplate = faceppTemplate;
	}

	/**
	 * Returns the Face++ configuration properties.
	 *
	 * @return the {@link FaceppProperties} instance
	 */
	protected FaceppProperties getFaceppProperties() {
		return faceppTemplate.getFaceppProperties();
	}

	/**
	 * Returns the OkHttp3 template used for HTTP communication.
	 *
	 * @return the {@link FaceppOkHttp3Template} instance
	 */
	protected FaceppOkHttp3Template getFaceppOkHttp3Template(){
		return faceppTemplate.getFaceppOkHttp3Template();
	}

	/**
	 * Returns the Jackson {@link ObjectMapper} for JSON serialization/deserialization.
	 *
	 * @return the {@link ObjectMapper} instance
	 */
	protected ObjectMapper getObjectMapper(){
		return faceppTemplate.getFaceppOkHttp3Template().getObjectMapper();
	}
}
