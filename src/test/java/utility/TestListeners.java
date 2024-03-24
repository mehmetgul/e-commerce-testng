package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListeners implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = Driver.getDriver();
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.FAIL, "Test Failed");
        test.fail(result.getThrowable());

        //Screenshot
        if(result.getStatus() == ITestResult.FAILURE){
            if(driver instanceof TakesScreenshot){
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                String base64ScreenShot = takesScreenshot.getScreenshotAs(OutputType.BASE64);
                String failedMethodName = result.getMethod().getMethodName();
                test.addScreenCaptureFromBase64String(base64ScreenShot,failedMethodName);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.log(Status.SKIP, "Test Skipped");
    }


    @Override
    public void onStart(ITestContext context) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = "target/test-output/MyExtentReport" + date + ".html";
        ExtentSparkReporter htmlReport = new ExtentSparkReporter(fileName);

        //Report name
        htmlReport.config().setReportName("Automation Test Results");
        htmlReport.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReport);

        //Optional
        extent.setSystemInfo("Environment", System.getProperty("env"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "defaultBrowser"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
