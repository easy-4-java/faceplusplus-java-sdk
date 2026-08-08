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

/**
 * Async-capable operations for face detection, analysis, comparison, search, and skin analysis.
 * Extends {@link FaceppFaceOperations} inheriting all synchronous face operation methods.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppFaceOperations
 * @see FaceppTemplate
 */
public class FaceppFaceAsyncOperations extends FaceppFaceOperations {

	/**
	 * Constructs a new {@code FaceppFaceAsyncOperations} bound to the given template.
	 *
	 * @param faceppTemplate the template providing access to SDK configuration and HTTP client
	 */
	public FaceppFaceAsyncOperations(FaceppTemplate faceppTemplate) {
		super(faceppTemplate);
	}

}
