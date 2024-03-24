package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.library.FlowsLibrary;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Utility extends FlowsLibrary {

    public static boolean isClickable(WebElement element, int waitTime) {

        try {

            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementVisible(WebElement element, int waitTime) {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void scrollTo(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    public static void scrollToCenter(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:\"center\"})", element);
    }

    public static void waits(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void hoverOver(WebElement element, int waitTime) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).pause(Duration.ofSeconds(waitTime)).build().perform();
        // actions.moveToElement(element).build().perform();
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public void clickElementWithWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    public void clearElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
    }

    public void sendKeyToElement(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    public void selectElementByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void selectElementByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public String getTextElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    public void clickWithJSExecutor(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public void sendKeyWithJSExecutor(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript(("arguments[0].value=" + text + ";"), element);
    }

    public void switchToIframeByIdOrName(String idOrName) {
        Driver.getDriver().switchTo().frame(idOrName);
    }

    public void switchToIframeByIndex(int index) {
        Driver.getDriver().switchTo().frame(index);
    }

    public void switchToIframeByWebElement(WebElement iframeElement) {
        Driver.getDriver().switchTo().frame(iframeElement);
    }

    public void switchToParentFrame() {
        Driver.getDriver().switchTo().parentFrame();
    }

    public void switchToDefaultContent() {
        Driver.getDriver().switchTo().defaultContent();
    }

    public boolean isTextPresentInElement(WebElement element, String text, int waitTime) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTime));
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public void switchToNewWindow() {
        String currentWindow = Driver.getDriver().getWindowHandle();
        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            if (!windowHandle.equals(currentWindow)) {
                Driver.getDriver().switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void switchToAlertAndAccept(int waitTime) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTime));
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void captureScreenshot(String filenameWithPath) {
        File src = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(filenameWithPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesElementExist(By locator, int waitTime) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(waitTime));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
