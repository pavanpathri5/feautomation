package pages.herokuapp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.AssertUtil;
import utils.RestUtil;
import utils.SeleniumUtils;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class HomePage {
    private static final Logger logger= LogManager.getLogger(HomePage.class);
    public enum HomePageElement {
        AB_TEST,
        ADD_REMOVE_ELEMENTS,
        ADDELEMENT_BUTTON,
        DELETEELEMENT_BUTTON,

        BASIC_AUTH,

        BROKEN_IMAGES_LINK,
        IMAGES,
        CHALLENGING_DOM,
        CHALLENGING_DOM_TABLE,
        CHECKBOXES,

        CHECKBOXES1,

        CHECKBOXES2,
        CONTEXTMENU,

        HOTSPOT
        // Add more as needed
    }

    private static final Map<HomePageElement, By> locators = new EnumMap<>(HomePageElement.class);

    static {
        locators.put(HomePageElement.AB_TEST, By.xpath("//a[@href='/abtest']"));
        locators.put(HomePageElement.ADD_REMOVE_ELEMENTS, By.xpath("//a[@href='/add_remove_elements/']"));
        locators.put(HomePageElement.ADDELEMENT_BUTTON, By.xpath("//button[@onclick='addElement()']"));
        locators.put(HomePageElement.DELETEELEMENT_BUTTON, By.xpath("//button[@onclick='deleteElement()']"));
        locators.put(HomePageElement.BASIC_AUTH, By.xpath("//a[@href='/basic_auth']"));
        locators.put(HomePageElement.BROKEN_IMAGES_LINK, By.xpath("//a[@href='/broken_images']"));
        locators.put(HomePageElement.IMAGES, By.tagName("img"));
        locators.put(HomePageElement.CHALLENGING_DOM, By.xpath("//a[@href='/challenging_dom']"));
        locators.put(HomePageElement.CHALLENGING_DOM_TABLE, By.xpath("//table"));
        locators.put(HomePageElement.CHECKBOXES, By.xpath("//a[@href='/checkboxes']"));
        locators.put(HomePageElement.CHECKBOXES1, By.xpath("//form[@id='checkboxes']/input[1]"));
        locators.put(HomePageElement.CHECKBOXES2, By.xpath("//form[@id='checkboxes']/input[2]"));
        locators.put(HomePageElement.CONTEXTMENU, By.xpath("//a[@href='/context_menu']"));
        locators.put(HomePageElement.HOTSPOT, By.xpath("//div[@id='hot-spot']"));
        // Add more as needed
    }

    public By getLocator(HomePageElement element) {
        return locators.get(element);
    }


    // Sample usage:
    // SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.AB_TEST));

    public java.util.List<org.openqa.selenium.WebElement> getAllImages() {
        return utils.SeleniumUtils.findAll(getLocator(HomePageElement.IMAGES));
    }

    public int getImageCount() {
        return utils.SeleniumUtils.count(getLocator(HomePageElement.IMAGES));
    }

    public Boolean verifyBrokenImages(){
        List<String> links=SeleniumUtils.getAttributes(getLocator(HomePageElement.IMAGES),"src");
        for(String link:links){
            logger.info("link : "+link);
            RestAssured.baseURI=link;
            Response response=RestUtil.get();
            if(response.statusCode()!=200){
                return false;
            }
        }
        return true;
    }

    // Extracts the challenging DOM table as a List<HashMap<String, String>>
    public List<java.util.HashMap<String, String>> getChallengingDomTableData() {
        List<java.util.HashMap<String, String>> tableData = new java.util.ArrayList<>();
        org.openqa.selenium.WebElement table = utils.SeleniumUtils.find(getLocator(HomePageElement.CHALLENGING_DOM_TABLE));
        List<org.openqa.selenium.WebElement> headers = table.findElements(org.openqa.selenium.By.cssSelector("thead th"));
        List<String> headerNames = headers.stream().map(org.openqa.selenium.WebElement::getText).toList();
        List<org.openqa.selenium.WebElement> rows = table.findElements(org.openqa.selenium.By.cssSelector("tbody tr"));
        for (org.openqa.selenium.WebElement row : rows) {
            List<org.openqa.selenium.WebElement> cells = row.findElements(org.openqa.selenium.By.tagName("td"));
            java.util.HashMap<String, String> rowData = new java.util.HashMap<>();
            for (int i = 0; i < headerNames.size() && i < cells.size(); i++) {
                rowData.put(headerNames.get(i), cells.get(i).getText());
            }
            tableData.add(rowData);
        }
        return tableData;
    }

    // Dynamic locator for checkbox by name
    public By getCheckboxByName(String name) {
        return By.xpath("//form[@id='checkboxes']/input[@name='" + name + "']");
    }

    // Example usage:
    // SeleniumUtils.check(homePage.getCheckboxByName("checkbox1"));
    // SeleniumUtils.uncheck(homePage.getCheckboxByName("checkbox2"));

    public void checkCheckbox(HomePageElement checkbox) {
        if (!SeleniumUtils.isChecked(getLocator(checkbox))) {
            SeleniumUtils.click(getLocator(checkbox));
        }
    }

    public void uncheckCheckbox(HomePageElement checkbox) {
        if (SeleniumUtils.isChecked(getLocator(checkbox))) {
            SeleniumUtils.click(getLocator(checkbox));
        }
    }

    public boolean isCheckboxChecked(HomePageElement checkbox) {
        return SeleniumUtils.isChecked(getLocator(checkbox));
    }
}
