package utility;

import com.github.pireba.applescript.AppleScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UploadFile {
    private static final Logger logger = LoggerFactory.getLogger(UploadFile.class);

    public void selectFileToUpload(String filePath, String browser) {
        String hostSystem = System.getProperty("os.name").toLowerCase();
        logger.info("Host System: {}", hostSystem);

        if (hostSystem.contains("windows")) {
            executeWindowsScript(filePath);
        } else if (hostSystem.contains("mac")) {
            executeMacScript(filePath, browser);
        } else {
            logger.error("Unsupported operating system for upload functionality.");
        }
    }

    private void executeWindowsScript(String filePath) {
        String script = System.getProperty("user.dir") + "\\src\\test\\java\\com\\ecommerce\\utility\\script.vbs";
        try {
            Runtime.getRuntime().exec(new String[]{"wscript", script, filePath});
        } catch (IOException e) {
            logger.error("Failed to execute script for Windows", e);
        }
    }

    private void executeMacScript(String filePath, String browser) {
        String appleScriptTemplate = "activate application \"%s\"\n" +
                "tell application \"System Events\"\n" +
                "  delay 3\n" +
                "  keystroke \"G\" using {command down, shift down}\n" +
                "  delay 3\n" +
                "  keystroke \"%s\"\n" + // Replacing ###PATH### with %s
                "  delay 2\n" +
                "  keystroke return\n" +
                "  delay 2\n" +
                "  keystroke return\n" +
                "end tell";

        String browserName = getBrowserNameForAppleScript(browser);
        if (browserName.isEmpty()) {
            logger.error("Browser Configuration Error: Unsupported browser - {}", browser);
            return;
        }

        // filePath.replace("\\", "/") - Ensuring file path is in correct format for AppleScript
        String appleScript = String.format(appleScriptTemplate, browserName, filePath.replace("\\", "/"));

        try {
            AppleScript as = new AppleScript(appleScript);
            String result = as.executeAsString();
            logger.info("AppleScript Execution Result: {}", result);
        } catch (Exception e) {
            logger.error("Script could not be executed for MAC", e);
        }
    }

    private String getBrowserNameForAppleScript(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return "Google Chrome";
            case "firefox":
                return "Firefox";
            case "safari":
                return "Safari";
            case "edge":
                return "Microsoft Edge";
            default:
                return "";
        }
    }
}
