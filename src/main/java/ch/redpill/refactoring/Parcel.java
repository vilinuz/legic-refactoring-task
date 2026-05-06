package ch.redpill.refactoring;

import java.util.Objects;

public record Parcel(String code, Type type) {
    enum Type {
        NEWSPAPER,
        MAGAZINE
    }

    public Parcel {
        Objects.requireNonNull(code, "Parcel code must not be null");
        Objects.requireNonNull(type, "Parcel type must not be null");
    }

}
