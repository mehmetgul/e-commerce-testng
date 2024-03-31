package tests;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "searchTerm")
    public Object[][] searchTerms() {
        return new Object[][]{
                {"Dress"},
                {"T-shirt"},
                {"Blouse"}
        };
    }
}
