package com.lwreid.platform.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverFactory {
    private static WebDriver webDriver;
    private static List<WebDriverThread> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());

    @BeforeSuite
    public void instantiateDriverObject() {
        webDriver = new ThreadLocal<WebDriverThread>() {
            @Override
            protected WebDriverThread initialValue() {
                WebDriverThread webDriverThread = new WebDriverThread();
                webDriverThreadPool.add(webDriverThread);
                webDriver = webDriverThread.getDriver();
                return webDriverThread;
            }
        }.get().getDriver();
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }

    @AfterSuite
    public void closeDriverObjects() throws Exception {
        webDriverThreadPool.forEach(WebDriverThread::quitDriver);
        webDriverThreadPool.clear();
    }
}