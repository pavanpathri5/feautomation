package utils;

import io.qameta.allure.Allure;
import org.testng.asserts.SoftAssert;

public class AssertUtil {
    private static final ThreadLocal<SoftAssert> softassert = new ThreadLocal<>();

    public static SoftAssert get(){
        if(softassert.get()==null){
            softassert.set(new SoftAssert());
        }
        return softassert.get();
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        Allure.step("Assert Equals: " + message +
                " | Expected: " + expected + " | Actual: " + actual);
        get().assertEquals(actual, expected, message);
    }

    public static void assertTrue(boolean condition, String message) {
        Allure.step("Assert True: " + message);
        get().assertTrue(condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        Allure.step("Assert False: " + message);
        get().assertFalse(condition, message);
    }

    public static void assertAll(){
        Allure.step("Assert All - Verifying all soft Assertions");
        get().assertAll();
        softassert.remove();
    }
}
