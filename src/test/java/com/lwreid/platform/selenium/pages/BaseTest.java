package com.lwreid.platform.selenium.pages;

import com.lwreid.platform.selenium.driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest extends DriverFactory {
    static WebDriver webDriver;
    private final String baseUrl = System.getProperty("base.url");

    public static WebDriver getDriver() {
        return webDriver;
    }

    @BeforeSuite
    public void setUpSuite() {
        webDriver = getWebDriver();
        webDriver.manage().window().maximize();
        //webDriver.get(baseUrl);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        webDriver.manage().deleteAllCookies();
        //getDriver().get("http://lwreid:lwreid206@staging.lwreid.com.au.tmp.anchor.net.au/")
        getDriver().get("http://lwreid.com.au/");
    }

    @AfterSuite
    public void tearDown() throws Exception {
        if (webDriver != null) webDriver.quit();
    }
}