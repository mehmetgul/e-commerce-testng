package utility.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadExcelLib {
    public Object[][] getExcelData(String filename, String sheetName, int numberOfCols) {
        File file = new File(filename);

        Object[][] arr;

        try (FileInputStream excel = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(excel);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int start = sheet.getFirstRowNum();
            int end = sheet.getLastRowNum();
            arr = new Object[end - start + 1][numberOfCols];
            for (int i = start; i <= end; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows
                for (int j = 0; j < numberOfCols; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                arr[i][j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                arr[i][j] = cell.getNumericCellValue();
                                break;
                            case BOOLEAN:
                                arr[i][j] = cell.getBooleanCellValue();
                                break;
                            case FORMULA:
                                arr[i][j] = cell.getCellFormula();
                                break;
                            default:
                                arr[i][j] = null;
                        }
                    } else {
                        arr[i][j] = null; // Handle null cells
                    }
                }
            }
        } catch (IOException e) {
            // Consider using a logger or throwing a custom exception
            System.err.println("Error reading Excel file: " + e.getMessage());
            return new Object[0][0]; // Return an empty array in case of error
        }
        return arr;
    }
}
