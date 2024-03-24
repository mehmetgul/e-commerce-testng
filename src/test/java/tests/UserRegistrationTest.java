package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utility.ConfigReader;

public class UserRegistrationTest extends TestBase {

    @Test(groups = {"regression", "smoke"},
            description = "ECM-21 UI | Valid Registration")
    public void createNewAccount() {
        String webSiteUrl = ConfigReader.getProperty("url");

        getAppLibrary().getFlowsLibrary().navigateToUrl(webSiteUrl);
        boolean result = getAppLibrary().getPageLibrary().getUserRegistrationPage().createNewRegistration();
        Assert.assertTrue(result);

        String actualResult = getAppLibrary().getPageLibrary().getUserRegistrationPage().getRegistrationPageTitle();
        System.out.println("actualResult = " + actualResult);
        Assert.assertTrue(actualResult.contains("account"));
    }
}
