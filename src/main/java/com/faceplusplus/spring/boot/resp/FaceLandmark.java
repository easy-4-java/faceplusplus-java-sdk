package com.faceplusplus.spring.boot.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents a facial landmark point or region returned by the Face++ API.
 * Contains position and dimension information for a specific facial feature.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceDetectResponse
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceLandmark {

    /** Y-coordinate of the landmark region top-left corner (in pixels). */
    @JsonProperty("top")
    private Integer top;

    /** X-coordinate of the landmark region top-left corner (in pixels). */
    @JsonProperty("left")
    private Integer left;

    /** Width of the landmark region (in pixels). */
    @JsonProperty("width")
    private Integer width;

    /** Height of the landmark region (in pixels). */
    @JsonProperty("height")
    private Integer height;

}
