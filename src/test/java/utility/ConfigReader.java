package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConfigReader {
    private static Properties configFile = new Properties();
    //ReentrantLock is used to ensure that only one thread can execute a block of code
    // at a time, which is crucial for avoiding race conditions and ensuring
    // thread safety in multithreaded applications.
    private static final Lock lock = new ReentrantLock();
    // Assumes config.properties is directly under the project root, adjust if needed.
    private static final String DEFAULT_CONFIG_PATH = "configuration.properties";

    static {
        // Load the default configuration from the project folder
        loadDefaultProperties(DEFAULT_CONFIG_PATH);
        // Override with environment-specific configurations from the classpath/resources
        //if same configuration available in config.properties
        overrideWithEnvironmentSpecificProperties();
    }

    private static void loadDefaultProperties(String filePath) {
        lock.lock();
        try {
            if (Files.exists(Paths.get(filePath))) {
                try (InputStream input = new FileInputStream(filePath)) {
                    configFile.load(input);
                }
            } else {
                throw new RuntimeException("Unable to find default configuration file: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load default configuration file: " + filePath, e);
        } finally {
            lock.unlock();
        }
    }

    private static void loadPropertiesFromClasspath(String resourcePath) {
        lock.lock();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                System.out.println("Environment-specific configuration file not found: " + resourcePath);
                return; // Don't throw an exception to allow the application to work with default configs
            }
            configFile.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from classpath: " + resourcePath, e);
        } finally {
            lock.unlock();
        }
    }

    private static void overrideWithEnvironmentSpecificProperties() {
        String environment = System.getProperty("env"); // No default value here
        if (environment != null && !environment.trim().isEmpty()) {
            String envPath = environment + ".properties";
            loadPropertiesFromClasspath(envPath);
        }
    }

    public static String getProperty(String key) {
        lock.lock();
        try {
            return configFile.getProperty(key);
        } finally {
            lock.unlock();
        }
    }
}
