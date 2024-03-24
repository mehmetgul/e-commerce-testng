package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.Driver;

public class UserRegistrationElements {
    public UserRegistrationElements() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//div[@class='header_user_info']/a")
    public WebElement signInButton;

    @FindBy(xpath = "//input[@id='email_create']")
    public WebElement emailAddress;

    @FindBy(xpath = "//span[normalize-space()='Create an account']")
    public WebElement createAccountButton;

    @FindBy(xpath = "//input[@id='id_gender2']")
    public WebElement mrsCheckBox;

    @FindBy(xpath = "//input[@id='customer_firstname']")
    public WebElement firstName;

    @FindBy(xpath = "//input[@id='customer_lastname']")
    public WebElement lastName;

    @FindBy(xpath = "//input[@id='passwd']")
    public WebElement password;

    @FindBy(xpath = "//select[@id='days']")
    public WebElement day;

    @FindBy(xpath = "//select[@id='months']")
    public WebElement month;

    @FindBy(xpath = "//select[@id='years']")
    public WebElement year;

    @FindBy(xpath = "//span[normalize-space()='Register']")
    public WebElement register;
}
