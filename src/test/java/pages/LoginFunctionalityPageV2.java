package pages;

import elements.LoginFunctionalityElements;
import org.testng.Assert;
import utility.ConfigReader;

public class LoginFunctionalityPageV2 extends PageBase {
    LoginFunctionalityElements loginFunctionalityElements = new LoginFunctionalityElements();

    public void loginFunctionality() {
        clickElement(loginFunctionalityElements.signInLink);
        sendKeyToElement(loginFunctionalityElements.email, ConfigReader.getProperty("email"));
        sendKeyToElement(loginFunctionalityElements.password, ConfigReader.getProperty("password"));
        clickElementWithWait(loginFunctionalityElements.signInButton);
        String actualResult = getTextElement(loginFunctionalityElements.accountCheck);
        String expectedResult = "MY ACCOUNT";
        Assert.assertEquals(actualResult,expectedResult,"Didn't match");
    }
}
