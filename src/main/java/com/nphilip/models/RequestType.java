package com.nphilip.models;

public enum RequestType {
    NEW_ITEM_CREATION,
    ITEM_DELETION,
    GET_JSON_DATA;

    @Override
    public String toString() {
        return this.name();
    }
}
