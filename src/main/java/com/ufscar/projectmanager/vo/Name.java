package com.ufscar.projectmanager.vo;

public class Name {

    private final String value;

    public Name(String value) {
        if (value.length() > 100) {
            throw new IllegalArgumentException("Project name too long.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
