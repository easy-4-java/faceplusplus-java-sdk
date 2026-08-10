package com.faceplusplus.spring.boot.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Summary information for a FaceSet, typically used in lists or nested within
 * face detail responses. Contains the FaceSet token, tags, and outer_id.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceDetailResponse
 * @see FacesetListResponse.FacesetFetail
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceSetFetail {

    /** FaceSet token identifier. */
    @JsonProperty("faceset_token")
    private String facesetToken;

    /** Comma-separated custom tags for grouping FaceSets. Max 255 characters. */
    @JsonProperty("tags")
    private String tags;

    /** User-defined FaceSet identifier. Empty if not defined. */
    @JsonProperty("outer_id")
    private String outerId;

}
