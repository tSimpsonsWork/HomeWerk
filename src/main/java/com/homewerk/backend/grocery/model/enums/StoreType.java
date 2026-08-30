package com.homewerk.backend.grocery.model.enums;

public enum StoreType {

    KROGER("Kroger"),
    PUBLIX("Publix"),
    DIERBERGS("Dierbergs"),
    SCHNUCKS("Schnucks");

    private final String displayName;

    StoreType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}