package pages;

import com.github.javafaker.Faker;
import utility.*;
import utility.excel.CsvUtils;
import utility.excel.ExcelUtil;
import utility.excel.ReadExcelLib;

public class PageBase extends Utility {
    private final CsvUtils csvUtils;
    private final Database database;
    private final ExcelUtil excelUtil;
    private final Faker faker;
    private final GetEmailConfig getEmailConfig;
    private final SmsFetcher smsFetcher;
    private final ReadExcelLib readExcelLib;
    private final UploadFile uploadFile;

    public PageBase(){
        csvUtils =new CsvUtils();
        database = new Database();
        excelUtil =new ExcelUtil();
        faker =new Faker();
        getEmailConfig =new GetEmailConfig();
        smsFetcher = new SmsFetcher();
        readExcelLib = new ReadExcelLib();
        uploadFile = new UploadFile();
    }

    public CsvUtils getCsvUtils() {
        return csvUtils;
    }

    public Database getDatabase() {
        return database;
    }

    public ExcelUtil getExcelUtil() {
        return excelUtil;
    }

    public Faker getFaker() {
        return faker;
    }

    public GetEmailConfig getGetEmailConfig() {
        return getEmailConfig;
    }

    public SmsFetcher getSmsFetcher() {
        return smsFetcher;
    }

    public ReadExcelLib getReadExcelLib() {
        return readExcelLib;
    }

    public UploadFile getUploadFile() {
        return uploadFile;
    }
}
