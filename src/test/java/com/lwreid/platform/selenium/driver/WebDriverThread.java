package com.lwreid.platform.selenium.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Optional;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

public class WebDriverThread {

    private WebDriver webdriver;
    private DriverType selectedDriverType;

    private static String sessId;
    private final DriverType defaultDriverType = DriverType.FIREFOX;
    private final String browser = System.getProperty("browser", defaultDriverType.toString()).toUpperCase();
    //private final String browser = defaultDriverType.toString().toUpperCase();
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final Boolean useRemoteWebDriver = Boolean.parseBoolean(System.getProperty("remote"));
    private final Boolean useServices = Boolean.parseBoolean(System.getProperty("use.services"));
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private static final String HUB = System.getProperty("gridURL");
    private static final String SERVICES_USR = System.getProperty("s.user");
    private static final String SERVICES_KEY = System.getProperty("s.key");
    private static final String SAUCE_URL =
            String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub", SERVICES_USR, SERVICES_KEY);
    private static final String BSTACK_URL =
            String.format("http://%s:%s@hub-cloud.browserstack.com/wd/hub", SERVICES_USR, SERVICES_KEY);


    public WebDriver getDriver() {
        if (null == webdriver) {
            Proxy proxy = null;
            if (proxyEnabled) {
                proxy = new Proxy();
                proxy.setProxyType(MANUAL);
                proxy.setHttpProxy(proxyDetails);
                proxy.setSslProxy(proxyDetails);
            }
            determineEffectiveDriverType();
            DesiredCapabilities desiredCapabilities = selectedDriverType.getDesiredCapabilities(proxy);
            instantiateWebDriver(desiredCapabilities);
        }
        return webdriver;
    }

    void quitDriver() {
        Optional.ofNullable(webdriver).ifPresent(WebDriver::quit);
    }

    private void determineEffectiveDriverType() {
        DriverType driverType = defaultDriverType;
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown getDriver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No getDriver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public static String getSessId() {
        return sessId;
    }

    private void instantiateWebDriver(DesiredCapabilities desiredCapabilities) {
        System.out.println(" ");
        System.out.println("Current Operating System: " + operatingSystem);
        System.out.println("Current Architecture: " + systemArchitecture);
        System.out.println("Current Browser Selection: " + selectedDriverType);
        System.out.println(" ");

        try {
            if (useRemoteWebDriver != null && useRemoteWebDriver) {
                URL seleniumGridURL = null;

                if (useServices)
                    seleniumGridURL = new URL(SAUCE_URL)/* : new URL(HUB)*/;
                else
                    seleniumGridURL = new URL(HUB);

                String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
                String desiredPlatform = System.getProperty("desiredPlatform");

                if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                    desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
                }

                if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                    desiredCapabilities.setVersion(desiredBrowserVersion);
                }
                webdriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
                sessId = ((RemoteWebDriver) webdriver).getSessionId().toString();
            } else {
                webdriver = selectedDriverType.getWebDriverObject(desiredCapabilities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

