import config.ConfigManager;
import drivers.DriverManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import pages.herokuapp.HomePage;

public class RegressionTest extends BaseTest {
    @Epic("App Launch")
    @Feature("App Launch & close")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "verify open url and close browser")
    public void firstTest() throws InterruptedException {
        DriverManager.getDriver().get(ConfigManager.get("baseUrl"));
        HomePage homePage = new HomePage();
        homePage.clickHomeButton();
    }

}
