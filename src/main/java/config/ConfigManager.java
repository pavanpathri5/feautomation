package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties props = new Properties();
    private static final String DEFAULT_ENV = "UAT1";

    static {
        try {
            String env = System.getProperty("env");
            if (env == null || env.isEmpty()) {
                env = System.getenv("ENV");
            }
            if (env == null || env.isEmpty()) {
                env = DEFAULT_ENV;
            }
            String resourceName = env + ".properties";
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
            if (is == null) {
                throw new RuntimeException("Properties file not found in classpath: " + resourceName);
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String getEnv() {
        return get("env");
    }

    public static String getRunMode() {
        return get("runMode");
    }
}
