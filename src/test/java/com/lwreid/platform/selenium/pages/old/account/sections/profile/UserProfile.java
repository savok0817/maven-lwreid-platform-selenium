package com.lwreid.platform.selenium.pages.old.account.sections.profile;

import com.lwreid.platform.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Nikita Ovsyannikov on 16.01.2017.
 */
public class UserProfile extends BasePage {
    private WebDriverWait wait;

    public UserProfile() {
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "#account")
    WebElement myAccountButton;

    @FindBy(css = "#projects")
    WebElement myPhotobooksButton;

    @FindBy(css = ".create-button a")
    WebElement createBookButton;

    @Step
    public UserProfile clickMyAccount() {
        wait.until(ExpectedConditions.visibilityOf(myAccountButton));
        myAccountButton.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#user-form")));
        return this;
    }

    @Step
    public UserProfile clickMyPhotoBook() {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(myPhotobooksButton));
        myPhotobooksButton.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#userContent")));
        return this;
    }

    @Step
    public UserProfile clickCreatePhotoBook() {
        waitForAnimation();
        createBookButton.click();
        waitForNetwork(10);
        return this;
    }

}
