package com.faceplusplus.spring.boot.req;

import com.faceplusplus.spring.boot.FaceppApiAddress;

import java.util.function.Function;

/**
 * Enumeration of skin analysis types, each mapping to a specific Face++ API endpoint.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 3.0.0
 * @see FaceppApiAddress#FACE_SKIN_ANALYZE
 * @see FaceppApiAddress#FACE_SKIN_ANALYZE_ADVANCED
 * @see FaceppApiAddress#FACE_SKIN_ANALYZE_PRO
 */
public enum SkinAnalyzeType {

    /** Basic skin analysis. */
    BASIC((x) -> {
        return FaceppApiAddress.FACE_SKIN_ANALYZE;
    }),

    /** Advanced skin analysis with additional metrics. */
    ADVANCED((x) -> {
        return FaceppApiAddress.FACE_SKIN_ANALYZE_ADVANCED;
    }),

    /** Professional skin analysis with full metrics. */
    PRO((x) -> {
        return FaceppApiAddress.FACE_SKIN_ANALYZE_PRO;
    });

    Function<Object, FaceppApiAddress> function;

    SkinAnalyzeType(Function<Object, FaceppApiAddress> function){
        this.function = function;
    }

    /**
     * Returns the API address corresponding to this skin analysis type.
     *
     * @return the {@link FaceppApiAddress} for this analysis type
     */
    public FaceppApiAddress getApiAddress(){
        return function.apply(this);
    }


}
