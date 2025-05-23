import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ParsingFile {

    private ClassLoader cl = ParsingFile.class.getClassLoader();

    @Test
    @DisplayName("Проверка наличия файлов в zip архиве")
    void parsingZipFileTest() throws Exception {

        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("archive.zip"))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        }
    }


    @Test
    @DisplayName("Чтение pdf файла из архива")
    void pdfFileParsingTest() throws Exception {
        boolean pdfFile = false;
        try (InputStream inputStream = cl.getResourceAsStream("archive.zip");
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith(".pdf")) {
                    pdfFile = true;
                    PDF pdf = new PDF(zipInputStream);
                    Assertions.assertEquals("PDF Form Example", pdf.title);
                    Assertions.assertTrue(pdf.text.contains("This is an example of a user fillable PDF form"));
                }
            }
        }
        Assertions.assertTrue(pdfFile, "PDF файл не найден в архиве");
    }


    @Test
    @DisplayName("Чтение xls файла из архива")
    void xlsFileParsingTest() throws Exception {
        boolean xlsFile = false;
        try (InputStream inputStream = cl.getResourceAsStream("archive.zip"); ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith(".xls")) {
                    xlsFile = true;
                    XLS xls = new XLS(zipInputStream);
                    String titleName = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    String titleNameOne = xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue();
                    String titleNameTwo = xls.excel.getSheetAt(0).getRow(2).getCell(0).getStringCellValue();
                    String titleNameThree = xls.excel.getSheetAt(0).getRow(3).getCell(0).getStringCellValue();
                    String IdentifierTitle = xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue();
                    String IdentifierOne = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    String IdentifierTwo = xls.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue();
                    String IdentifierThree = xls.excel.getSheetAt(0).getRow(3).getCell(1).getStringCellValue();
                    String creator = xls.excel.getSheetAt(0).getRow(0).getCell(6).getStringCellValue();
                    String creatorOne = xls.excel.getSheetAt(0).getRow(1).getCell(6).getStringCellValue();
                    String creatorTwo = xls.excel.getSheetAt(0).getRow(2).getCell(6).getStringCellValue();
                    String creatorThree = xls.excel.getSheetAt(0).getRow(3).getCell(6).getStringCellValue();
                    Assertions.assertEquals("Title", titleName);
                    Assertions.assertEquals("My Title", titleNameOne);
                    Assertions.assertEquals("Another title", titleNameTwo);
                    Assertions.assertEquals("The best image ever", titleNameThree);
                    Assertions.assertEquals("Identifier", IdentifierTitle);
                    Assertions.assertEquals("2016.1.1", IdentifierOne);
                    Assertions.assertEquals("2016.1.2", IdentifierTwo);
                    Assertions.assertEquals("2015.4.5", IdentifierThree);
                    Assertions.assertEquals("Creator", creator);
                    Assertions.assertEquals("John Doe", creatorOne);
                    Assertions.assertEquals("John Doe", creatorTwo);
                    Assertions.assertEquals("Mary Maryson", creatorThree);
                }
            }
        }
        Assertions.assertTrue(xlsFile, "xls файл не найден в архиве");
    }


    @Test
    @DisplayName("Чтение csv файла из архива")
    void csvFileParsingTest() throws Exception {
        boolean csvFile = false;
        try (InputStream inputStream = cl.getResourceAsStream("archive.zip"); ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith(".csv")) {
                    csvFile = true;
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zipInputStream));
                    List<String[]> content = csvReader.readAll();
                    org.assertj.core.api.Assertions.assertThat(content).contains(
                            new String[]{"Андрей", " 25"},
                            new String[]{"Виктор", " 31"},
                            new String[]{"Генадий", " 18"},
                            new String[]{"Дмитрий", " 23"},
                            new String[]{"Елисей", " 49"}
                    );
                }
            }
        }
        Assertions.assertTrue(csvFile, "csv файл не найден в архиве");
    }
}
