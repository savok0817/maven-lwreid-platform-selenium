package com.lwreid.platform.selenium.driver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
            /*String s = File.separator;
            String path = System.getProperty("user.dir") + s + "selenium_standalone" + s + "windows" + s +
                    "marionette" + s + "64bit" + s + "geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", path);*/
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
            return addProxySettings(capabilities, proxySettings);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            FirefoxProfile ffProfile = new FirefoxProfile();
            /*ffProfile.setPreference("browser.download.manager.showWhenStarting",false);
            ffProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/plain, application/vnd.ms-excel, text/csv, text/comma-separated-values, application/octet-stream");
            ffProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
            ffProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
            ffProfile.setPreference("browser.download.manager.useWindow", false);
            ffProfile.setPreference("browser.download.folderList", 2);
            ffProfile.setPreference("browser.download.dir", Other.DOWNLOAD_DIR_LOCAL);*/
            return new FirefoxDriver(ffProfile);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities(Proxy proxySettings) {
            System.setProperty("webdriver.chrome.driver", determinateDriverPath(this));
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Collections.singletonList("--no-default-browser-check"));
            //capabilities.setCapability("download.prompt_for_download", false);
            //capabilities.setCapability("UserProfile.default_content_settings.popups", 0);
            HashMap<String, String> chromePreferences = new HashMap<>();
            //chromePreferences.put("UserProfile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);
            return new DesiredCapabilities(capabilities);
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            //options.addArguments("--start-maximized");
            Map<String, Object> prefs = new HashMap<String, Object>();
            /*prefs.put("download.default_directory", Other.DOWNLOAD_DIR_LOCAL);
            prefs.put("download.prompt_for_download", false);
            prefs.put("UserProfile.default_content_settings.popups", 0);*/
            options.setExperimentalOption("prefs", prefs);
            return new ChromeDriver(options);
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities(Proxy setting) {
            System.setProperty("webdriver.ie.driver", determinateDriverPath(this));
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setBrowserName("internet explorer");
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability("nativeEvents", false);
            capabilities.setCapability("enablePersistentHover", false);
            capabilities.setCapability("javascriptEnabled", true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability("ie.ensureCleanSession", true);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new InternetExplorerDriver(capabilities);
        }
    };

    protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxySettings) {
        if (null != proxySettings) {
            capabilities.setCapability(PROXY, proxySettings);
        }
        return capabilities;
    }

    private static String determinateDriverPath(DriverType type) {
        String s = File.separator;
        boolean isMac = System.getProperty("os.name").toUpperCase().contains("MAC");
        String os = isMac ? "osx" : "windows";
        String pref = System.getProperty("user.dir") + s + "selenium_standalone" + s + os + s;
        if (type.equals(CHROME)) {
            String driver = isMac ? "chromedriver" : "chromedriver.exe";
            pref += "googlechrome"  + s + "64bit" + s + driver;
        } else
        if (type.equals(IE)) {
            pref += "internetexplorer"  + s + "64bit" + s + "IEDriverServer.exe";
        }
        return pref;
    }
}