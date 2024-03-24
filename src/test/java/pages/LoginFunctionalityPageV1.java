package pages;

import elements.LoginFunctionalityElements;
import org.testng.Assert;
import utility.ConfigReader;

public class LoginFunctionalityPageV1 {
LoginFunctionalityElements loginFunctionalityElements = new LoginFunctionalityElements();

    public void loginFunctionality() {
        loginFunctionalityElements.signInLink.click();
        loginFunctionalityElements.email.sendKeys(ConfigReader.getProperty("email"));
        loginFunctionalityElements.password.sendKeys(ConfigReader.getProperty("password"));
        loginFunctionalityElements.signInButton.click();

        Assert.assertEquals(loginFunctionalityElements.accountCheck.getText(),"MY ACCOUNT");
    }
}
