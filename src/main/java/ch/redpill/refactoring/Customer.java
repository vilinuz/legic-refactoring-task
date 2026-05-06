package ch.redpill.refactoring;


import java.util.List;

public record Customer(String name, List<Parcel> magazines) {
    public Customer(String name, List<Parcel> magazines) {
        this.name = name;
        this.magazines = List.copyOf(magazines);
    }
}
