package utils;

import config.ConfigManager;
import drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v136.network.Network;
import org.openqa.selenium.devtools.v136.network.model.Headers;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeleniumUtils {

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

    public static DevTools getDevTools() {
        WebDriver driver = DriverManager.getDriver();

        /*if (!(driver instanceof ChromeDriver)) {
            throw new UnsupportedOperationException("DevTools is only supported for ChromeDriver");
        }*/

        return ((ChromeDriver) driver).getDevTools();
    }

    public static void setBasicHeader(){
        DevTools devTools=getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        String auth = ConfigManager.get("username") + ":" + ConfigManager.get("password");
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("Authorization", "Basic " + encoded);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headersMap)));
    }

    // New: Find all elements matching a locator
    public static List<WebElement> findAll(By locator) {
        return DriverManager.getDriver().findElements(locator);
    }

    // New: Count elements matching a locator
    public static int count(By locator) {
        return findAll(locator).size();
    }

    // New: Get attribute values for all elements matching a locator
    public static List<String> getAttributes(By locator, String attribute) {
        return findAll(locator).stream()
                .map(e -> e.getAttribute(attribute))
                .collect(Collectors.toList());
    }

    // Add more wrappers as needed (isDisplayed, select, etc.)

    // Check if a checkbox is checked
    public static boolean isChecked(By locator) {
        return find(locator).isSelected();
    }

    // Check a checkbox if not already checked
    public static void check(By locator) {
        if (!isChecked(locator)) {
            click(locator);
        }
    }

    // Uncheck a checkbox if checked
    public static void uncheck(By locator) {
        if (isChecked(locator)) {
            click(locator);
        }
    }

    public static void rightClick(By locator){
        Actions actions = new Actions(DriverManager.getDriver());
        WebElement webElement=DriverManager.getDriver().findElement(locator);
        actions.contextClick(webElement).perform();
    }
}
