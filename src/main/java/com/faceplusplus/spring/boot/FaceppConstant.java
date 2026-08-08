package com.faceplusplus.spring.boot;

/**
 * Constants used across the Face++ SDK for API endpoints, recording identifiers,
 * and storage path configuration.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppApiAddress
 */
public interface FaceppConstant {

    /**
     * URL template for retrieving all users within a channel.
     * Placeholders: {0} = appId, {1} = channelName.
     */
    String URL_CHANNEL_USER = "https://api.agora.io/dev/v1/channel/user/{0}/{1}";

    /**
     * URL for user-ban rule management.
     */
    String URL_RULE = "https://api.agora.io/dev/v1/kicking-rule";

    /**
     * Default UID used for recording requests.
     */
    String RECORDING_UID = "10";

    /**
     * Root directory for video file storage (pattern: video/{yyyy-MM-dd}).
     */
    String VEIDO_PAHT = "video";
}
