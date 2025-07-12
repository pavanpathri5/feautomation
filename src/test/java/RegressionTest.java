import config.ConfigManager;
import drivers.BaseTest;
import drivers.DriverManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import pages.herokuapp.ChallengeDomTable;
import pages.herokuapp.HomePage;
import utils.AssertUtil;
import utils.RetryAnalyzer;
import utils.SeleniumUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegressionTest extends BaseTest {
    @Epic("App Launch")
    @Feature("App Launch & close")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "verify open url and close browser",retryAnalyzer = RetryAnalyzer.class)
    public void firstTest() throws InterruptedException {
        DriverManager.getDriver().get(ConfigManager.get("baseUrl"));
        HomePage homePage = new HomePage();
        ChallengeDomTable challengeDomTable=new ChallengeDomTable();
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.AB_TEST));
        //SeleniumUtils.type(homePage.getLocator(HomePage.HomePageElement.SEARCH_BOX), "query");
        //AssertUtil.assertTrue(SeleniumUtils.isVisible(homePage.getLocator(HomePage.HomePageElement.ADD_REMOVE_ELEMENTS)),"Verifying the add remove button");
        SeleniumUtils.navigateBack();
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.ADD_REMOVE_ELEMENTS));
        SeleniumUtils.navigateBack();
        //SeleniumUtils.setBasicHeader(); //Use only for chrome
        //SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.BASIC_AUTH)); //user only for chrome
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.BROKEN_IMAGES_LINK));
        SeleniumUtils.navigateBack();
        //AssertUtil.assertTrue(homePage.verifyBrokenImages(),"image broken link failed");
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.CHALLENGING_DOM));
        List<HashMap<String, String>> tableData=challengeDomTable.getTableData();
        for (Map<String, String> map : tableData) {
            System.out.println("  HashMap:");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("    " + entry.getKey() + ": " + entry.getValue());
            }
        }
        SeleniumUtils.navigateBack();
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.CHECKBOXES));
        SeleniumUtils.check(homePage.getLocator(HomePage.HomePageElement.CHECKBOXES1));
        SeleniumUtils.navigateBack();
        SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.CONTEXTMENU));
        SeleniumUtils.rightClick(homePage.getLocator(HomePage.HomePageElement.HOTSPOT));
        DriverManager.getDriver().switchTo().alert().accept();
        //SeleniumUtils.click(homePage.getLocator(HomePage.HomePageElement.CHECKBOXES2));
        Thread.sleep(5000);
        AssertUtil.assertAll();
    }
}
