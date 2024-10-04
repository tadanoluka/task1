package ru.tadanoluka.task1.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumeration for postal item types")
public enum PostalItemType {
    LETTER,
    PARCEL,
    PACKAGE,
    POSTCARD
}
