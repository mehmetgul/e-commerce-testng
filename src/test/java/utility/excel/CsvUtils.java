package utility.excel;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {
    public static boolean checkIf2CSVFilesAreSame(String csvFilePath1, String csvFilePath2) {

        List<String[]> allLines1 = null;
        List<String[]> allLines2 = null;
        try (CSVReader reader1 = new CSVReader(new FileReader(csvFilePath1));
             CSVReader reader2 = new CSVReader(new FileReader(csvFilePath2))) {
            allLines1 = reader1.readAll();
            allLines2 = reader2.readAll();
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            e.printStackTrace();
        } catch (IOException | CsvException e) {
            System.out.println("No Line");
            e.printStackTrace();
        }
        if (allLines1.size() != allLines2.size()) return false;

        for (int i = 0; i < allLines1.size(); i++) {
            if (!Arrays.equals(allLines1.get(i), allLines2.get(i))) {
                System.out.println("Test Fail in Line:" + (i + 1));
                System.out.println("Expected:");
                System.out.println(Arrays.toString(allLines1.get(i)));
                System.out.println("Actual:");
                System.out.println(Arrays.toString(allLines2.get(i)));
                return false;
            }
        }
        return true;
    }

    public String getDataFromCSV(int rowNum, int columnNum, String path) {
        String csvData = "";
        try (CSVParser parser = new CSVParser(new FileReader(path), CSVFormat.DEFAULT)) {
            List<CSVRecord> list = parser.getRecords();
            csvData = list.get(rowNum).get(columnNum);
            System.out.println("Read data: " + csvData);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return csvData;
    }

    //Read CSV file using OPENCSV
    public String readCSV(String searchValue, String path) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new BufferedReader(new FileReader(path)));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                if (nextLine[0].equals(searchValue)) {
                    return nextLine[1];
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void writeToCSV(String csvFilePath, List<String[]> dataLines) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeAll(dataLines);
        } catch (IOException e) {
            // Log or handle the exception
            throw e;
        }
    }

    //Add functionality to write to CSV files, which could be useful for generating
    // test data or logging test results.
    public List<String[]> readCSV(String searchValue, int columnIndex, String path) {
        List<String[]> matchingRows = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[columnIndex].equals(searchValue)) {
                    matchingRows.add(nextLine);
                }
            }
        } catch (IOException | CsvException e) {
            // Log or handle the exception
        }
        return matchingRows;
    }

    //Method to merge multiple CSV files into one, which could be useful for consolidating test data.
    public void mergeCSVFiles(List<String> csvFilePaths, String outputPath) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(outputPath));
        for (String filePath : csvFilePaths) {
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                List<String[]> lines = reader.readAll();
                writer.writeAll(lines, true); // true to avoid writing headers multiple times
            } catch (CsvException e) {
                // Handle the exception
            }
        }
        writer.close();
    }


}
