package pages.herokuapp;

import org.openqa.selenium.By;
import utils.SeleniumUtils;

public class HomePage {
    private final By abTest = By.xpath("//a[@href='/abtest']");

    public void clickHomeButton() {
        SeleniumUtils.click(abTest);
    }
}
