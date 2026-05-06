# Simple Refactoring Example

This project is a demonstration of modernizing Java code through refactoring. It showcases the transition from imperative loops to a functional approach using Java Streams and Records.

## Project Overview

The application processes delivery rounds, customers, and their parcels to generate formatted barcode records. The main logic is encapsulated in the `RoundFileWriter` class, which filters and formats data based on parcel types (Magazines vs. Newspapers).

### Key Features

- **Modern Java Patterns**: Utilizes Java Records for domain models (`Round`, `Customer`, `Parcel`).
- **Functional Programming**: Uses Java Streams, `flatMap`, `Predicate`, and `Collectors` for data processing.
- **Automated Testing**: Comprehensive JUnit 4 test suite to ensure refactoring correctness.

## Domain Model

The project uses a hierarchical model:

- **Round**: Represents a delivery route containing multiple customers.
- **Customer**: Represents a recipient with a list of parcels.
- **Parcel**: Represents an item with a specific code and type (Newspaper or Magazine).

## Getting Started

### Prerequisites

- Java 21 or higher (configured for Java 25 in this environment).
- Gradle (Wrapper included).

### Running Tests

To execute the test suite and verify the refactoring:

```bash
./gradlew clean test
```

## Refactoring Highlights

The `RoundFileWriter` was refactored to:

1. **Eliminate Deep Nesting**: Replaced three levels of `for` loops with a flattened stream pipeline.
2. **Improve Readability**: Extracted complex mapping and filtering logic into dedicated private methods (`getCustomerParcels`, `matchType`, `createBarcodeRecord`).
3. **Immutability**: Leveraged Java Records to ensure thread-safety and reduce boilerplate code.
