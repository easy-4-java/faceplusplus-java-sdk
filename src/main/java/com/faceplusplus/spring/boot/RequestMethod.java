package com.faceplusplus.spring.boot;

/**
 * Enumeration of HTTP request methods supported by the Face++ SDK.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FaceppApiAddress
 */
public enum RequestMethod {

    /** HTTP GET method. */
    GET,

    /** HTTP HEAD method. */
    HEAD,

    /** HTTP POST method. */
    POST,

    /** HTTP PUT method. */
    PUT,

    /** HTTP PATCH method. */
    PATCH,

    /** HTTP DELETE method. */
    DELETE,

    /** HTTP OPTIONS method. */
    OPTIONS,

    /** HTTP TRACE method. */
    TRACE
}
