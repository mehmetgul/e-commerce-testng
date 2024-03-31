package pages;

import elements.SearchFunctionalityElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 *  1- perform search
 *  2- is Title Correct
 *  3- are Results correct
 */
public class SearchFunctionalityPage extends PageBase{
    private static final Logger logger = LogManager.getLogger(SearchFunctionalityPage.class);
    SearchFunctionalityElements searchFunctionalityElements = new SearchFunctionalityElements();

    public void performSearch(String searchTerm){
        clickElement(searchFunctionalityElements.searchBox);
        sendKeyToElement(searchFunctionalityElements.searchBox,searchTerm );
        clickElement(searchFunctionalityElements.searchButton);
    }

    public boolean isTitleCorrect(String expectedPageTitle){
        logger.info(getCurrentPageTitle());
        return getCurrentPageTitle().equals(expectedPageTitle);
    }

    public boolean areResultsRelevant(String searchKey){
        for(WebElement searchResult: searchFunctionalityElements.searchResults){
            String productTitle=searchResult.findElement(By.cssSelector("h5>a")).getText();
            if(!productTitle.contains(searchKey)){
                return false;
            }
        }
        return true;
    }

}
