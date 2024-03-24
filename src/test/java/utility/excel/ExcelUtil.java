package utility.excel;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/*
 * This is a utility class for reading from writing to Excel files.
 */
public class ExcelUtil {

    private Sheet workSheet;
    private Workbook workBook;
    private String path;

    // Add an initialize method to set up or reset the ExcelUtil instance
    public void initialize(String path, String sheetName) {
        this.path = path;
        try {
            FileInputStream excelFile = new FileInputStream(path);
            this.workBook = WorkbookFactory.create(excelFile);
            this.workSheet = workBook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize ExcelUtil with path: " + path + " and sheetName: " + sheetName, e);
        }
    }

    //Get the data of specific cell
    public String getCellData(int rowNum, int colNum) {
        try {
            Cell cell = workSheet.getRow(rowNum).getCell(colNum);
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            return null;
        }
    }

    //This method checking the text from first column only
    public Cell searchFirstColumnCellData(String text) {
        for (Row row : workSheet) {
            Cell cell = row.getCell(5);
            String cellValue = String.valueOf(cell.getCellType());
            System.out.println("cellValue = " + cellValue);
            if (cellValue.equalsIgnoreCase("NUMERIC")) {
                System.out.println("### " + cell.getNumericCellValue());
                if (text.equals(String.valueOf(cell.getNumericCellValue())))
                    return cell;
            } else {
                System.out.println(cell.getStringCellValue());
                if (text.equals(String.valueOf(cell.getStringCellValue())))
                    return cell;
            }
        }
        return null;
    }

    //This method will search the given text from entire sheet
    public Cell searchCellData(String text) {
        for (Row row : workSheet) {
            for (Cell cell : row) {
                String cellValue = String.valueOf(cell.getCellType());
                if (cellValue.equalsIgnoreCase("NUMERIC")) {
                    if (text.equals(String.valueOf(cell.getNumericCellValue())))
                        return cell;
                } else {
                    if (text.equals(cell.getStringCellValue()))
                        return cell;
                }
            }
        }
        return null;
    }

    public String[][] getDataArray() {
        String[][] data = new String[rowCount()][columnCount()];
        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;
    }

    /**
     * Get data as a List<Map<<String, String>>,
     * key => column name
     */
    public List<Map<String, String>> getDataList() {
        // get all columns
        List<String> columns = getColumnsNames();
        // this will be returned
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // create map of the row using the column and value
            // column map key, cell value --> map value
            Map<String, String> rowMap = new HashMap<>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }
            data.add(rowMap);
        }
        return data;
    }

    // Get column names
    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();

        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }

    public int columnCount() {
        return workSheet.getRow(0).getLastCellNum();
    }

    public int rowCount() {
        return workSheet.getPhysicalNumberOfRows();
    }

    public List<String> getColumnData(int columnIndex) {
        // this will be returned
        List<String> data = new ArrayList<>();
        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Cell cell = workSheet.getRow(i).getCell(columnIndex);
            data.add(cell.getStringCellValue());
        }
        return data;
    }

    public boolean isSheetExist(String sheetName) {
        for (Sheet sheet : workBook) {
            if (sheet.getSheetName().equals(sheetName)) {
                return true;
            }
        }
        return false;
    }

    //Adding support for writing data to Excel files could be beneficial for test data setup or result logging.
    public void setCellData(int rowNum, int colNum, String data) {
        try {
            Row row = workSheet.getRow(rowNum);
            if (row == null) {
                row = workSheet.createRow(rowNum);
            }
            Cell cell = row.getCell(colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(data);

            FileOutputStream outputStream = new FileOutputStream(path);
            workBook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to write data to Excel file", e);
        }
    }

    //Add a method for easily adding a new row with data.
    // This can streamline the process of adding data to an Excel sheet
    public void addRowWithData(List<Object> rowData) {
        int newRowNum = workSheet.getLastRowNum() + 1;
        Row row = workSheet.createRow(newRowNum);
        for (int i = 0; i < rowData.size(); i++) {
            Cell cell = row.createCell(i);
            Object value = rowData.get(i);
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof Date) {
                CellStyle cellStyle = workBook.createCellStyle();
                CreationHelper createHelper = workBook.getCreationHelper();
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                cell.setCellValue((Date) value);
                cell.setCellStyle(cellStyle);
            } // Consider more types as needed
        }
    }

    public void close() {
        try {
            this.workBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}