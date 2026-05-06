package ch.redpill.refactoring;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoundFileWriterTest {

    @Test
    public void magazineFileWritten() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE);
        String actualFileContents = roundFileWriter.writeMagazineFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n" + "South,Fred,23\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void newspaperParcelsNotWrittenIntoMagazineFile() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE, Parcel.Type.MAGAZINE, Parcel.Type.NEWSPAPER);
        String actualFileContents = roundFileWriter.writeMagazineFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void newspaperFileWritten() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER);
        String actualFileContents = roundFileWriter.writeNewspaperFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n" + "South,Fred,23\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    @Test
    public void magazineParcelsNotWrittenIntoNewspaperFile() {
        RoundFileWriter roundFileWriter = createRoundFileWriter(Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER, Parcel.Type.NEWSPAPER, Parcel.Type.MAGAZINE);
        String actualFileContents = roundFileWriter.writeNewspaperFile();
        String expectedFileContents = "North,Bloggs,123456\n" + "North,Bloggs,987\n" + "North,Jones,567\n";
        assertEquals(expectedFileContents, actualFileContents);
    }

    private RoundFileWriter createRoundFileWriter(Parcel.Type bloggs1Type, Parcel.Type bloggs2Type, Parcel.Type jonesType, Parcel.Type fredType) {
        Parcel bloggsParcel1 = new Parcel("123456", bloggs1Type);
        Parcel bloggsParcel2 = new Parcel("987", bloggs2Type);
        Customer bloggs = new Customer("Bloggs", List.of(bloggsParcel1, bloggsParcel2));

        Parcel jonesParcel = new Parcel("567", jonesType);
        Customer jones = new Customer("Jones", List.of(jonesParcel));

        Parcel fredParcel = new Parcel("23", fredType);
        Customer fred = new Customer("Fred", List.of(fredParcel));

        Round north = new Round("North", List.of(bloggs, jones));
        Round south = new Round("South", List.of(fred));

        return new RoundFileWriter(List.of(north, south));
    }
}
