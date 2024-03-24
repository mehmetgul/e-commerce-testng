package utility.library;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utility.Driver;

public class FlowsLibrary {

    private final WebDriver driver;

    public FlowsLibrary() {
        this.driver = Driver.getDriver();
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public String getCurrentPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollDown(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ")");
    }

    public void closeCurrentTab() {
        driver.close();
    }

    public void switchToNewTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void setWindowSize(int width, int height) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
    }

    public void switchToIframeByIdOrName(String idOrName) {
        driver.switchTo().frame(idOrName);
    }

    public void switchToIframeByIndex(int index) {
        driver.switchTo().frame(index);
    }

}
