package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.Driver;

import java.util.List;

public class SearchFunctionalityElements {
    public SearchFunctionalityElements(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
    @FindBy(name = "search_query")
    public WebElement searchBox;

    @FindBy(name = "submit_search")
    public WebElement searchButton;

    @FindBy(xpath = "//ul[@class='product_list grid row']/li")
    public List<WebElement> searchResults;
}
