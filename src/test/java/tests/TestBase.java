package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utility.Driver;
import utility.library.AppLibrary;

import java.time.Duration;

public class TestBase {
    AppLibrary appLibrary;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup() {
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        appLibrary = new AppLibrary();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Driver.close();
    }

    public AppLibrary getAppLibrary() {
        return appLibrary;
    }
}
