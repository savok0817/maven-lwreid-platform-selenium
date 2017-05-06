package com.lwreid.platform.selenium.pages.main;

import com.lwreid.platform.selenium.pages.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import javax.swing.*;

/**
 * Created by Nikita Ovsyannikov on 16.01.2017.
 */
public class MainPage extends BasePage {
    private WebDriverWait wait;
    private Actions actions;

    public MainPage() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "a[title='login']")
    WebElement loginButton;

    @Step
    public void clickLogin() {
        loginButton.click();
        waitForPageLoad();
    }
}
