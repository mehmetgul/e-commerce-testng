package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.Driver;

public class LoginFunctionalityElements {
    public LoginFunctionalityElements(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(className = "login")
    public WebElement signInLink;

    @FindBy(id = "email")
    public WebElement email;

    @FindBy(id = "passwd")
    public WebElement password;

    @FindBy(id="SubmitLogin")
    public WebElement signInButton;

    @FindBy(className = "page-heading")
    public WebElement accountCheck;

    @FindBy(className = "info-account")
    public WebElement welcomeMessage;
}
