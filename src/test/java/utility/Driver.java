package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    private static final Logger logger = LogManager.getLogger(Driver.class);
    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            initializeDriver();
        }
        return driverPool.get();
    }

    private static void initializeDriver() {
        // Load environment-specific properties
        String environment = System.getProperty("env", "QA"); // Default to "qa" if not specified
        System.out.println("environment = " + environment);
        logger.info("===============================================================");
        logger.info("Environment : {}", environment.toUpperCase());
        logger.info("Operating System : {}", System.getProperty("os.name"));
        logger.info("Browser: {}", ConfigReader.getProperty("browser"));
        logger.info("===============================================================");

        String browser = System.getProperty("browser", ConfigReader.getProperty("browser"));
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driverPool.set(initChromeDriver());
                    break;
                case "chrome_headless":
                    driverPool.set(initChromeHeadlessDriver());
                    break;
                case "firefox":
                    driverPool.set(initFirefoxDriver());
                    break;
                case "firefox_headless":
                    driverPool.set(initFirefoxHeadlessDriver());
                    break;
                case "ie":
                    driverPool.set(initInternetExplorerDriver());
                    break;
                case "edge":
                    driverPool.set(initEdgeDriver());
                    break;
                case "safari":
                    driverPool.set(initSafariDriver());
                    break;
                case "remote_chrome":
                    driverPool.set(initRemoteWebDriver(new ChromeOptions()));
                    break;
                case "remote_firefox":
                    driverPool.set(initRemoteWebDriver(new FirefoxOptions()));
                    break;
                default:
                    throw new UnsupportedOperationException("Browser " + browser + " is not supported.");
            }
        } catch (Exception e) {
            logger.error("Error initializing the driver: {}", e.getMessage(), e);
        }
    }

    private static WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        configureChromeOptions(options);
        return new ChromeDriver(options);
    }

    private static WebDriver initChromeHeadlessDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        configureChromeOptions(options);
        return new ChromeDriver(options);
    }

    private static WebDriver initFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(firefoxProfile());
        return new FirefoxDriver(options);
    }

    private static WebDriver initFirefoxHeadlessDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless=new");
        return new FirefoxDriver(options);
    }

    private static WebDriver initInternetExplorerDriver() {
        return new InternetExplorerDriver();
    }

    private static WebDriver initEdgeDriver() {
        return new EdgeDriver();
    }

    private static WebDriver initSafariDriver() {
        return null;
    }

    private static WebDriver initRemoteWebDriver(Capabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444//wd/hub"), capabilities);
        } catch (Exception e) {
            logger.error("Failed to initialize remote web driver: {}", e.getMessage(), e);
            return null;
        }
    }

    private static void configureChromeOptions(ChromeOptions options) {
        Map<String, Object> prefs = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> contentSettings = new HashMap<>();
        // Setting default download directory
        String downloadFilepath = ConfigReader.getProperty("download.default_directory");
        prefs.put("download.default_directory", downloadFilepath);
        // Disable Chrome's PDF viewer to download PDF files automatically
        prefs.put("plugins.always_open_pdf_externally", true);
        options.setExperimentalOption("prefs", prefs);

        contentSettings.put("cookies", ConfigReader.getProperty("cookiesEnableDisable"));
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--remote-allow-origins=*");
    }

    public static FirefoxProfile firefoxProfile() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        try {
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference("browser.download.dir", ConfigReader.getProperty("download.default_directory"));
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
            firefoxProfile.setPreference("pdfjs.disabled", true);
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
        } catch (Exception e) {
            logger.error("Profile can't be configured for Firefox: {}", e.getMessage(), e);
        }
        return firefoxProfile;
    }

    public static void close() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
