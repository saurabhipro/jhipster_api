package com.melontech.landsys.domain.enumeration;

/**
 * The HissaType enumeration.
 */
public enum HissaType {
    SINGLE_OWNER("Single Owner"),
    JOINT_OWNER("Joint Owner"),
    DIVIDED("divided");

    private final String value;

    HissaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
