package drivers;

// Example: String someConfig = config.ConfigManager.get("someKey");
// Use ConfigManager to fetch config values as needed
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static Proxy proxy;
    private static final ThreadLocal<WebDriver> driver=new ThreadLocal<>();

    public static WebDriver getDriver(){
        logger.debug("Getting WebDriver instance from ThreadLocal");
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance){
        logger.debug("Setting WebDriver instance in ThreadLocal");
        driver.set(driverInstance);
    }

    public static void quitDriver(){
        if(driver.get()!=null){
            logger.info("Quitting WebDriver instance and removing from ThreadLocal");
            driver.get().quit();
            driver.remove();
        }
    }
}
