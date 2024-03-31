package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.ConfigReader;

public class SearchFunctionalityTest extends TestBase {

    /**
     * 1- perform search
     * 2- is Title Correct
     * 3- are Results correct
     */

    @Test(groups = {"regression", "smoke"}, dataProviderClass = TestDataProvider.class,
            dataProvider = "searchTerm",
            description = "ECM-28 | UI | Search with an valid Product Name")
    public void searchProduct(String searchKey) {
        String expectedTitle= "Search - My Store";
        String homepageUrl = ConfigReader.getProperty("url");
        getAppLibrary().getFlowsLibrary().navigateToUrl(homepageUrl);
        getAppLibrary().getPageLibrary().getSearchFunctionalityPage().performSearch(searchKey);
        boolean verifyPageTitle = getAppLibrary().getPageLibrary().getSearchFunctionalityPage().isTitleCorrect(expectedTitle);
        Assert.assertTrue(verifyPageTitle);
        boolean verifyProductSearchResults = getAppLibrary().getPageLibrary().getSearchFunctionalityPage().areResultsRelevant(searchKey);
        Assert.assertTrue(verifyProductSearchResults);
    }


}
