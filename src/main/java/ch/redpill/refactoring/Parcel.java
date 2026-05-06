package ch.redpill.refactoring;

public record Parcel(String code, Type type) {
    enum Type {
        NEWSPAPER,
        MAGAZINE
    }
}
