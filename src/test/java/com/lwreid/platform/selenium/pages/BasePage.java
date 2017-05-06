package com.lwreid.platform.selenium.pages;

import com.lwreid.platform.selenium.listeners.TestListener;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import java.io.File;
import java.util.List;


public class BasePage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private static String imageDir;

    static  {
        String s = File.separator;
        imageDir = System.getProperty("user.dir") + s + "src" + s + "test" + s + "resources" + s + "imgs";
    }

    public BasePage() {
        driver = BaseTest.webDriver;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public WebDriver getDriver() {
        return driver;
    }

    protected void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until((ExpectedCondition<Boolean>) wdriver -> ((JavascriptExecutor) driver).executeScript(
                "return document.readyState"
        ).equals("complete"));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".logo-image-wrapper")));
    }

    protected boolean isErrorPagePresented() {
        return isElementPresented(By.cssSelector(".site-error"));
    }

    protected void clickWithJS(WebElement element) {
        final String javaScript = "if(document.createEvent){" +
                "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initEvent('click', true, false);" + "" +
                "arguments[0].dispatchEvent(evObj);" +
                "} else if(document.createEventObject){" +
                "arguments[0].fireEvent('onclick');" +
                "}";
        ((JavascriptExecutor) driver).executeScript(javaScript, element);
    }

    protected void clickSimpleWithJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected void waitForNetwork(int timeoutInSeconds) {
        System.out.println("Checking active ajax calls::");
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                for (int i = 0; i < timeoutInSeconds; i++) {
                        Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                        if (numberOfAjaxConnections instanceof Long) {
                            Long n = (Long) numberOfAjaxConnections;
                            System.out.println("Number of active ajax calls: " + n);
                            if (n == 0L)
                                break;
                        }
                        wait(1000);
                }
            } else {
                System.out.println("Web getDriver: " + driver + " cannot execute javascript");
            }
    }

    public void waitForAnimation() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait(2000);
        wait.until((ExpectedCondition<Boolean>) wdriver -> ((JavascriptExecutor) driver).executeScript(
                "return document.readyState"
        ).equals("complete"));
    }

    protected WebElement scrollToElement(WebElement element, boolean isIntoView) {
        ((JavascriptExecutor) driver).executeScript(String.format("arguments[0].scrollIntoView(%b);", isIntoView),
                element);
        return element;
    }

    protected void scroll(int y) {
        String script = String.format("scroll(0, %d);", y);
        ((JavascriptExecutor) driver).executeScript(script);
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    protected Screenshot getElementScreenshot(WebElement element) {
        return new AShot().takeScreenshot(driver, element);
    }

    protected WebElement waitForElementNotMove(final WebElement element) {
        wait.until((ExpectedCondition<Boolean>) wdriver -> {
            Point loc = element.getLocation();
            wait(500);
            return element.getLocation().equals(loc);
        });
        return element;
    }

    protected void setElementAttributeWithJS(String attributeName, String attributeValue, WebElement webElement) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", webElement, attributeName,
                attributeValue);
    }

    @Step
    public ImageDiff getDiff(Screenshot start, Screenshot end) {
        ImageDiff diff = new ImageDiffer().makeDiff(start, end);
        TestListener.attachScreenshot(diff);
        return diff;
    }

    protected void typeText(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }


    /**
     * Created to avoid boilerplate try\catches
     * @param by income locator
     * @return bool result
     */
    protected boolean isElementPresented(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementLocatedBy(By by) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception x) {
            return false;
        }
    }

    protected boolean isElementPresentedWithWait(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresentedWithWait(By by) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresented(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isAttributeContainsWithWait(WebElement element, String attribute, String value) {
        try {
            return wait.until(ExpectedConditions.attributeContains(element, attribute, value));
        } catch (Exception x){
            return false;
        }
    }

    protected boolean isAttributeContains(WebElement element, String attribute, String value) {
        try {
            return element.getAttribute(attribute).contains(value);
        } catch (Exception x){
            return false;
        }
    }

    protected String getTextFromElement(WebElement input) {
        scrollToElement(input, true);
        return input.getAttribute("value").trim();
    }

    protected void scrollAndClick(WebElement element){
        scrollToElement(element, false);
        element.click();
    }

    protected boolean isCheckBoxChecked(final String inputLocator) {
        String exp = String.format("return document.querySelector(\"%s\").checked", inputLocator);
        return (Boolean) ((JavascriptExecutor) driver).executeScript(exp);
    }

    protected boolean isCheckBoxChecked(WebElement element) {
        String exp = "return arguments[0].checked";
        return (Boolean) ((JavascriptExecutor) driver).executeScript(exp, element);
    }
}
