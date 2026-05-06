package ch.redpill.refactoring;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundFileWriter {
	private final List<Round> rounds;

	public RoundFileWriter(List<Round> rounds) {
		this.rounds = List.copyOf(rounds);
	}

	public String writeMagazineFile() {
		return writeFile(Parcel.Type.MAGAZINE);
	}

	public String writeNewspaperFile() {
		return writeFile(Parcel.Type.NEWSPAPER);
	}

	private String writeFile(final Parcel.Type type) {
		Objects.requireNonNull(type, "Parcel type must not be null");
		return rounds.stream()
				.flatMap(round -> round.customers().stream()
						.flatMap(customer -> getCustomerParcels(round, customer, type)))
				.collect(Collectors.joining());
	}

	private Stream<String> getCustomerParcels(Round round, Customer customer, Parcel.Type type) {
		return customer.parcels().stream()
				.filter(matchType(type))
				.map(parcel -> createBarcodeRecord(round, customer, parcel));
	}

	private Predicate<Parcel> matchType(Parcel.Type type) {
		return parcel -> parcel.type() == type;
	}

	private String createBarcodeRecord(Round round, Customer customer, Parcel parcel) {
		return String.join(",", round.name(), customer.name(), parcel.code()) + "\n";
	}

}