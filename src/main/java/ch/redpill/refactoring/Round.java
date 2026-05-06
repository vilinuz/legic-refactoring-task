package ch.redpill.refactoring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Round(String name, List<Customer> customers) {
    public Round {
        Objects.requireNonNull(name, "Round name must not be null");
        customers = Optional.ofNullable(customers)
                .map(List::copyOf)
                .orElse(Collections.emptyList());
    }

    public Round addCustomer(Customer customer) {
        List<Customer> updatedCustomers = new ArrayList<>(this.customers);
        updatedCustomers.add(customer);
        return new Round(this.name, updatedCustomers);
    }
}
