package utils;

import drivers.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount=0;
    private final int maxRetryCOunt=0;
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCOunt) {
            retryCount++;

            // 👇 Add screenshot capture on retry failure
            try {
                byte[] screenshot =  ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Retry-Failed-Screenshot-Attempt-" + retryCount,
                        new ByteArrayInputStream(screenshot));
            } catch (Exception e) {
                System.out.println("Screenshot capture failed during retry");
            }

            return true;
        }
        return false;
    }
}
