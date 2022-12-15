package framework.base;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected static Browser browser;

    @BeforeMethod
    public static void setUp() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @AfterMethod
    public static void tearDown() {
        browser.quit();
    }
}