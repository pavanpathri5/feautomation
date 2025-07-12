package pages.herokuapp;

import org.openqa.selenium.By;
import utils.SeleniumUtils;
import java.util.EnumMap;
import java.util.Map;

public class HomePage {
    public enum HomePageElement {
        AB_TEST,
        ADD_REMOVE_ELEMENTS,
        ADDELEMENT_BUTTON,
        DELETEELEMENT_BUTTON,

        BASIC_AUTH,
        // Add more as needed
    }

    private static final Map<HomePageElement, By> locators = new EnumMap<>(HomePageElement.class);

    static {
        locators.put(HomePageElement.AB_TEST, By.xpath("//a[@href='/abtest']"));
        locators.put(HomePageElement.ADD_REMOVE_ELEMENTS, By.xpath("//a[@href='/add_remove_elements/']"));
        locators.put(HomePageElement.ADDELEMENT_BUTTON, By.xpath("//button[@onclick='addElement()']"));
        locators.put(HomePageElement.DELETEELEMENT_BUTTON, By.xpath("//button[@onclick='deleteElement()']"));
        locators.put(HomePageElement.BASIC_AUTH, By.xpath("//a[@href='/basic_auth']"));
        // Add more as needed
    }

    public By getLocator(HomePageElement element) {
        return locators.get(element);
    }

    // Sample usage:
    // SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.AB_TEST));
}
