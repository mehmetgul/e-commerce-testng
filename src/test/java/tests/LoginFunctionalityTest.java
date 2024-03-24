package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utility.ConfigReader;

public class LoginFunctionalityTest extends TestBase {

    @Test(groups = {"regression","smoke","login"},
    description = "ECM-23 UI | Test the login process with a valid username and password.")
    public void loginFunctionalityTest() {
        String webSiteUrl = ConfigReader.getProperty("url");

        getAppLibrary().getFlowsLibrary().navigateToUrl(webSiteUrl);
        String[] expectedResults = getAppLibrary().getPageLibrary().getLoginFunctionalityPage().loginFunctionality();

        String expectedAccountText = "MY ACCOUNT";
        String expectedWelcomeMessage = "Welcome to your account. Here you can manage all of your personal information and orders.";

        Assert.assertEquals(expectedResults[0], expectedAccountText);
        Assert.assertEquals(expectedResults[1], expectedWelcomeMessage);
    }
}
