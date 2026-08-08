package com.faceplusplus.spring.boot.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents the bounding rectangle of a detected face in an image.
 * Coordinates are in pixels relative to the top-left corner of the image.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceDetectResponse
 * @see FaceDetailResponse
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceRectangle {

    /** Y-coordinate of the top-left corner of the rectangle (in pixels). */
    @JsonProperty("top")
    private Integer top;

    /** X-coordinate of the top-left corner of the rectangle (in pixels). */
    @JsonProperty("left")
    private Integer left;

    /** Width of the face rectangle (in pixels). */
    @JsonProperty("width")
    private Integer width;

    /** Height of the face rectangle (in pixels). */
    @JsonProperty("height")
    private Integer height;

}
