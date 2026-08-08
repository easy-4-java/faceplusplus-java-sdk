package com.faceplusplus.spring.boot.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Contains detected facial attributes such as gender, age, smile, head pose, and blur.
 * Each attribute is an optional nested object returned when requested via the
 * {@code return_attributes} parameter.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceDetectResponse
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaceAttributes {

    /**
     * Gender analysis result. Value: "Male" or "Female".
     */
    @JsonProperty("gender")
    private FaceAttrValue gender;

    /**
     * Age analysis result. Value is a non-negative integer.
     */
    @JsonProperty("age")
    private FaceAttrValue age;

    /**
     * Smile analysis result. Contains a value [0,100] and a threshold.
     */
    @JsonProperty("smile")
    private FaceAttrSmile smile;

    /**
     * Head pose analysis result. Contains pitch, roll, and yaw angles in degrees [-180, 180].
     */
    @JsonProperty("headpose")
    private FaceAttrHeadpose headpose;

    /**
     * Blur analysis result for the face.
     */
    @JsonProperty("blur")
    private FaceAttrBlur blur;

    /**
     * Generic attribute value holder containing a single string value.
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FaceAttrValue {

        /** The attribute value as a string. */
        @JsonProperty("value")
        private String value;

    }


    /**
     * Smile attribute with a continuous value and a classification threshold.
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FaceAttrSmile {

        /**
         * Smile intensity value in the range [0,100], with 3 decimal places.
         * Higher values indicate stronger smiling.
         */
        @JsonProperty("value")
        private Float value;

        /**
         * Threshold above which the face is considered to be smiling.
         */
        @JsonProperty("threshold")
        private Float threshold;

    }

    /**
     * Head pose angles representing rotation in 3D space.
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FaceAttrHeadpose {

        /** Yaw angle (head shaking) in degrees [-180, 180]. */
        @JsonProperty("yaw_angle")
        private Float yaw;

        /** Pitch angle (head nodding) in degrees [-180, 180]. */
        @JsonProperty("pitch_angle")
        private Float pitch;

        /** Roll angle (head tilting) in degrees [-180, 180]. */
        @JsonProperty("roll_angle")
        private Float roll;

    }


    /**
     * Blur analysis result for the face.
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FaceAttrBlur {

        /** Yaw angle component of blur analysis. */
        @JsonProperty("yaw_angle")
        private Float yaw;

        /** Pitch angle component of blur analysis. */
        @JsonProperty("pitch_angle")
        private Float pitch;

        /** Roll angle component of blur analysis. */
        @JsonProperty("roll_angle")
        private Float roll;

    }

}
