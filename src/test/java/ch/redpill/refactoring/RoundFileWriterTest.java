package ch.redpill.refactoring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RoundFileWriterTest {

    @Test
    public void parcelConstructorShouldThrowOnNull() {
        assertThrows(NullPointerException.class, () -> new Parcel(null, Parcel.Type.MAGAZINE));
        assertThrows(NullPointerException.class, () -> new Parcel("123", null));
    }

    @Test
    public void customerConstructorShouldThrowOnNullName() {
        assertThrows(NullPointerException.class, () -> new Customer(null, List.of()));
    }

    @Test
    public void customerConstructorShouldHandleNullList() {
        Customer customer = new Customer("Name", null);
        assertEquals(List.of(), customer.parcels());
    }

    @Test
    public void customerShouldHaveImmutableList() {
        List<Parcel> mutableList = new ArrayList<>();
        mutableList.add(new Parcel("1", Parcel.Type.MAGAZINE));
        Customer customer = new Customer("Name", mutableList);

        assertThrows(UnsupportedOperationException.class,
                () -> customer.parcels().add(new Parcel("2", Parcel.Type.NEWSPAPER)));
    }

    @Test
    public void roundConstructorShouldThrowOnNullName() {
        assertThrows(NullPointerException.class, () -> new Round(null, List.of()));
    }

    @Test
    public void roundConstructorShouldHandleNullList() {
        Round round = new Round("Name", null);
        assertEquals(List.of(), round.customers());
    }

    @Test
    public void roundShouldHaveImmutableList() {
        List<Customer> mutableList = new ArrayList<>();
        mutableList.add(new Customer("C1", List.of()));
        Round round = new Round("Round", mutableList);

        assertThrows(UnsupportedOperationException.class, () -> round.customers().add(new Customer("C2", List.of())));
    }

    @Test
    public void roundFileWriterShouldThrowOnNullRounds() {
        assertThrows(NullPointerException.class, () -> new RoundFileWriter(null));
    }

    @Test
    public void roundFileWriterShouldHandleNullElementsInList() {
        List<Round> listWithNull = new ArrayList<>();
        listWithNull.add(null);
        assertThrows(NullPointerException.class, () -> new RoundFileWriter(listWithNull));
    }

    @Test
    public void shouldHandleZeroCustomersInRound() {
        Round emptyRound = new Round("Empty", List.of());
        RoundFileWriter writer = new RoundFileWriter(List.of(emptyRound));
        assertEquals("", writer.writeMagazineFile());
    }

    @Test
    public void shouldHandleZeroParcelsForCustomer() {
        Customer emptyCustomer = new Customer("Empty", List.of());
        Round round = new Round("Round", List.of(emptyCustomer));
        RoundFileWriter writer = new RoundFileWriter(List.of(round));
        assertEquals("", writer.writeMagazineFile());
    }

    @Test
    public void magazineFileWritten() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE,
                Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE);
        String actualFileContents = roundFileWriter.writeMagazineFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n"
                + "South,Fred,23\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void newspaperParcelsNotWrittenIntoMagazineFile() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE,
                Parcel.Type.MAGAZINE, Parcel.Type.NEWSPAPER);
        String actualFileContents = roundFileWriter.writeMagazineFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void newspaperFileWritten() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER,
                Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER);
        String actualFileContents = roundFileWriter.writeNewspaperFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n"
                + "South,Fred,23\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void magazineParcelsNotWrittenIntoNewspaperFile() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER,
                Parcel.Type.NEWSPAPER, Parcel.Type.MAGAZINE);
        String actualFileContents = roundFileWriter.writeNewspaperFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    private RoundFileWriter createRoundFileWriter(Parcel.Type bloggs1Type, Parcel.Type bloggs2Type,
            Parcel.Type jonesType, Parcel.Type fredType) {
        Customer bloggs = new Customer("Bloggs", null)
                .addParcel(new Parcel("123456", bloggs1Type))
                .addParcel(new Parcel("987", bloggs2Type));

        Customer jones = new Customer("Jones", null)
                .addParcel(new Parcel("567", jonesType));

        Customer fred = new Customer("Fred", null)
                .addParcel(new Parcel("23", fredType));

        Round north = new Round("North", null)
                .addCustomer(bloggs)
                .addCustomer(jones);

        Round south = new Round("South", null)
                .addCustomer(fred);

        return new RoundFileWriter(List.of(north, south));
    }
}
