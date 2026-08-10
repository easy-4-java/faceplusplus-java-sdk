package com.faceplusplus.spring.boot;

import java.text.MessageFormat;

/**
 * Enumeration of all Face++ API endpoint addresses.
 * Each constant holds a human-readable description, the HTTP method, and the URL template.
 * URL templates may contain {@code {0}}, {@code {1}}, etc. placeholders that are resolved
 * via {@link #getUrl(Object...)}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceppFaceOperations
 * @see FaceppFacesetOperations
 */
public enum FaceppApiAddress {

	// ---------------- Face Recognition APIs ------------------

	/**
	 * Face Detect API - detects faces in an image and returns face tokens.
	 */
	ACQUIRE_RESOURCE_ID("Face Detect API", RequestMethod.POST,"/facepp/v3/detect"),

	/**
	 * Face Analyze API - analyzes facial attributes and landmarks.
	 */
	START_CLOUD_RECORDING("Face Analyze API", RequestMethod.POST,"https://api.agora.io/v1/apps/{0}/cloud_recording/resourceid/{1}/mode/{2}/start"),

	/**
	 * Dense Landmark API - returns dense facial landmark points.
	 */
	UPDATE_CLOUD_RECORDING("Dense Landmark API", RequestMethod.POST,"https://api.agora.io/v1/apps/{0}/cloud_recording/resourceid/{1}/sid/{2}/mode/{3}/updateLayout"),

	/**
	 * Face Compare API - compares two faces for similarity.
	 */
	UPDATE_CLOUD_RECORDING_LAYOUT("Face Compare API", RequestMethod.POST,"https://api.agora.io/v1/apps/{0}/cloud_recording/resourceid/{1}/sid/{2}/mode/{3}/update"),

	/**
	 * Query cloud recording status.
	 */
	QUERY_CLOUD_RECORDING("Query Cloud Recording Status", RequestMethod.POST,"https://api.agora.io/v1/apps/{0}/cloud_recording/resourceid/{1}/sid/{2}/mode/{3}/query"),

	/**
	 * Stop cloud recording.
	 */
	STOP_CLOUD_RECORDING("Stop Cloud Recording", RequestMethod.POST,"https://api.agora.io/v1/apps/{0}/cloud_recording/resourceid/{1}/sid/{2}/mode/{3}/stop"),

	// ---------------- Project Management ------------------

	/** Create a project. */
	PROJECT_POST("Create Project", RequestMethod.POST,"https://api.agora.io/v1/project"),

	/** Get a specific project. */
	PROJECT_GET("Get Project", RequestMethod.POST,"https://api.agora.io/v1/project"),

	/** Get all projects. */
	PROJECTS_GET("Get All Projects", RequestMethod.POST,"https://api.agora.io/v1/projects"),

	/** Enable or disable a project. */
	PROJECT_STATUS_POST("Enable/Disable Project", RequestMethod.POST,"https://api.agora.io/v1/projects_status"),

	/** Get usage data for a specific project. */
	PROJECT_USAGE_GET("Get Project Usage", RequestMethod.POST,"https://api.agora.io/v3/usage"),

	/** Set recording server IP. */
	RECORDING_CONFIG_POST("Set Recording Server IP", RequestMethod.POST,"https://api.agora.io/v1/recording_config"),

	/** Enable or disable the primary App certificate. */
	SIGNKEY_POST("Enable/Disable Primary App Certificate", RequestMethod.POST,"https://api.agora.io/v1/signkey"),

	/** Reset the primary App certificate. */
	SIGNKEY_RESET_POST("Reset Primary App Certificate", RequestMethod.POST,"https://api.agora.io/v1/reset_signkey"),

	// ---------------- User Ban Rules ------------------

	/** Create a user ban rule. */
	KICKING_RULE_POST("Create User Ban Rule", RequestMethod.POST,"https://api.agora.io/v1/kicking-rule"),

	/** Get the list of user ban rules. */
	KICKING_RULE_GET("Get User Ban Rules", RequestMethod.POST,"https://api.agora.io/v1/kicking-rule"),

	/** Update the effective time of a user ban rule. */
	KICKING_RULE_PUT("Update User Ban Rule", RequestMethod.POST,"https://api.agora.io/v1/kicking-rule"),

	/** Delete a user ban rule. */
	KICKING_RULE_DELETE("Delete User Ban Rule", RequestMethod.POST,"https://api.agora.io/v1/kicking-rule"),

	// ---------------- FaceSet Management APIs ------------------

	/**
	 * Create a FaceSet - a collection for storing face tokens.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888391">API Documentation</a>
	 */
	FACESET_CREATE("Create FaceSet", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/create"),

	/**
	 * Delete a FaceSet.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888393">API Documentation</a>
	 */
	FACESET_DELETE("Delete FaceSet", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/delete"),

	/**
	 * Update FaceSet information.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888401">API Documentation</a>
	 */
	FACESET_UPDATE("Update FaceSet", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/update"),

	/**
	 * Get the list of FaceSets and their information.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888397">API Documentation</a>
	 */
	FACESET_LIST("Get FaceSet List", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/getfacesets"),

	/**
	 * Get detailed information of a FaceSet.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888395">API Documentation</a>
	 */
	FACESET_DETAIL("Get FaceSet Detail", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/getdetail"),

	/**
	 * Add face tokens to a FaceSet.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888389">API Documentation</a>
	 */
	FACE_ADD("Add Face", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/addface"),

	/**
	 * Remove face tokens from a FaceSet.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888399">API Documentation</a>
	 */
	FACE_REMOVE("Remove Face", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface"),

	/**
	 * Add face tokens to a FaceSet asynchronously.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/40622166">API Documentation</a>
	 */
	FACE_ADD_ASYNC("Add Face (Async)", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/async/addface"),

	/**
	 * Remove face tokens from a FaceSet asynchronously.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/40622169">API Documentation</a>
	 */
	FACE_REMOVE_ASYNC("Remove Face (Async)", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/faceset/async/removeface"),

	/**
	 * Query the status of an async add/remove face task.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/40622157">API Documentation</a>
	 */
	FACE_STATUS_ASYNC("Query Async Task Status", RequestMethod.POST," https://api-cn.faceplusplus.com/facepp/v3/faceset/async/task_status"),

	// ---------------- Face Management APIs ------------------

	/**
	 * Set a user-defined user_id for a face token.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888387">API Documentation</a>
	 */
	FACE_SET_USERID("Set Face User ID", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/face/setuserid"),

	/**
	 * Get detailed information about a face.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888385">API Documentation</a>
	 */
	FACE_GET_DETAIL("Get Face Detail", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/face/getdetail"),

	// ---------------- Face Recognition APIs ------------------

	/**
	 * Detect faces in an image.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888373">API Documentation</a>
	 */
	FACE_DETECT("Face Detect", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/detect"),

	/**
	 * Analyze facial attributes and landmarks.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888383">API Documentation</a>
	 */
	FACE_ANALYZE("Face Analyze", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/face/analyze"),

	/**
	 * Compare two faces for similarity.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4887586">API Documentation</a>
	 */
	FACE_COMPARE("Face Compare", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/compare"),

	/**
	 * Search for similar faces in a FaceSet.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/4888381">API Documentation</a>
	 */
	FACE_SEARCH("Face Search", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v3/search"),

	/**
	 * Basic skin analysis API.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/119745378">API Documentation</a>
	 */
	FACE_SKIN_ANALYZE("Skin Analyze - Basic", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v1/skinanalyze"),

	/**
	 * Advanced skin analysis API.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/140781002">API Documentation</a>
	 */
	FACE_SKIN_ANALYZE_ADVANCED("Skin Analyze - Advanced", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v1/skinanalyze_advanced"),

	/**
	 * Professional skin analysis API.
	 * @see <a href="https://console.faceplusplus.com.cn/documents/307316314">API Documentation</a>
	 */
	FACE_SKIN_ANALYZE_PRO("Skin Analyze - Pro", RequestMethod.POST,"https://api-cn.faceplusplus.com/facepp/v1/skinanalyze_pro"),
	 ;

	private String opt;
	private RequestMethod method;
	private String url;

    FaceppApiAddress(String opt, RequestMethod method, String url) {
		this.opt = opt;
		this.method = method;
		this.url = url;
	}

	/**
	 * Returns the human-readable description of this API endpoint.
	 *
	 * @return the description string
	 */
	public String getOpt() {
		return opt;
	}

	/**
	 * Returns the HTTP request method for this API endpoint.
	 *
	 * @return the {@link RequestMethod}
	 */
	public RequestMethod getMethod() {
		return method;
	}

	/**
	 * Returns the raw URL template string, without placeholder resolution.
	 *
	 * @return the URL template
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Returns the URL with placeholders resolved using the given arguments.
	 *
	 * @param args the values to substitute into the URL template placeholders
	 * @return the resolved URL string
	 */
	public String getUrl(Object ...args) {
		return MessageFormat.format(url, args);
	}

}
