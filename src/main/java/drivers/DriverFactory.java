package drivers;

import config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    public static WebDriver createInstance(String browser){
        String runMode = config.ConfigManager.getRunMode();
        String os = config.ConfigManager.get("os");
        logger.info("Creating WebDriver instance. Browser: " + browser + ", RunMode: " + runMode + ", OS: " + os);
        MutableCapabilities capabilities;
        switch(browser.toLowerCase()){
            case "firefox":
                capabilities = new FirefoxOptions();
                break;
            case "safari":
                capabilities = new SafariOptions();
                break;
            case "edge":
                capabilities = new EdgeOptions();
                break;
            default:
                ChromeOptions chromeOptions=new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setAcceptInsecureCerts(true);
                //chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                capabilities = chromeOptions;
                break;
        }

        if("grid".equals(runMode)){
            String gridUrl = "http://localhost:4444";
            try {
                logger.info("Starting RemoteWebDriver for grid at " + gridUrl);
                return new RemoteWebDriver(new URL(gridUrl),capabilities);
            } catch (MalformedURLException e) {
                logger.error("Malformed grid URL: " + gridUrl, e);
                throw new RuntimeException(e);
            }
        }
        else{
            if(browser.equals("firefox")){
                logger.info("Starting local FirefoxDriver");
                return new FirefoxDriver((FirefoxOptions) capabilities);
            }
            if(browser.equals("safari")){
                logger.info("Starting local SafariDriver");
                return new SafariDriver((SafariOptions) capabilities);
            }
            else{
                logger.info("Starting local ChromeDriver");
                return new ChromeDriver((ChromeOptions) capabilities);
            }
        }
    }
}
