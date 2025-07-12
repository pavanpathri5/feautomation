package utils;

import config.ConfigManager;
import drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SeleniumUtils {
    private static final int DEFAULT_TIMEOUT = 10;

    public static WebElement find(By locator) {
        WebDriver driver = DriverManager.getDriver();
        Wait<WebDriver> wait=new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Integer.parseInt(ConfigManager.get("defaulttimeout"))))
                .pollingEvery(Duration.ofMillis(Integer.parseInt(ConfigManager.get("pollinginterval"))));

        return wait.until((webDriver) -> webDriver.findElement(locator));
    }

    public static void click(By locator) {
        find(locator).click();
    }

    public static void type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    public static String getText(By locator) {
        return find(locator).getText();
    }

    public static void navigateBack(){
        DriverManager.getDriver().navigate().back();
    }

    public static Boolean isVisible(By locator){
        try {
            return find(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    // Add more wrappers as needed (isDisplayed, select, etc.)
}
