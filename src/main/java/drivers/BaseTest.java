package drivers;

import config.ConfigManager;
import drivers.DriverFactory;
import drivers.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Listeners({AllureTestNg.class})
public class BaseTest {
    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        DriverManager.setDriver(DriverFactory.createInstance(resolveBrowser(browser)));
    }

    private String resolveBrowser(String xmlBrowserParam) {
        if (xmlBrowserParam != null && !xmlBrowserParam.isEmpty()) {
            return xmlBrowserParam;
        }

        String cliBrowser = System.getProperty("browser");
        if (cliBrowser != null && !cliBrowser.isEmpty()) {
            return cliBrowser;
        }

        return ConfigManager.get("browser"); // fallback to config.properties
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                WebDriver driver = DriverManager.getDriver();
                if (driver instanceof TakesScreenshot) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.getLifecycle().addAttachment("Screenshot on Failure", "image/png", "png", screenshot);
                }
            } catch (Exception e) {
                // Optionally log error
            }
        }
        DriverManager.quitDriver();
    }
}
