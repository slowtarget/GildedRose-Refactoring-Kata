package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextFixtureTest {

    public static void main(String[] args) throws IOException {
        // run this to regenerate the template files
        writeFile("output_50.txt", runFixture(50));
    }

    @Test
    public void testFiftyDays() throws IOException {
        String expected = readFile("output_50.txt");
        String actual = runFixture(50);
        assertEquals(expected, actual);
    }

    private static String runFixture(int days) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream original = System.out;
        System.setOut(ps);

        TexttestFixture.main(new String[]{"" + days});

        System.setOut(original);
        return baos.toString();
    }

    private static String readFile(String name) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getFileName(name))));
    }

    private static void writeFile(String name, String content) throws IOException {
        FileWriter fw = new FileWriter(getFileName(name));
        fw.write(content);
        fw.close();
    }

    private static String getFileName(String name) {
        return "src/test/golden-master/resources/expected/" + name;
    }
}
