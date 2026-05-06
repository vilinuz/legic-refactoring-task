package ch.redpill.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Customer(String name, List<Parcel> parcels) {
    public Customer {
        Objects.requireNonNull(name, "Customer name must not be null");
        parcels = Optional.ofNullable(parcels)
                .map(List::copyOf)
                .orElse(Collections.emptyList());
    }

    public Customer addParcel(Parcel parcel) {
        List<Parcel> updatedParcels = new ArrayList<>(this.parcels);
        updatedParcels.add(parcel);
        return new Customer(this.name, updatedParcels);
    }
}
