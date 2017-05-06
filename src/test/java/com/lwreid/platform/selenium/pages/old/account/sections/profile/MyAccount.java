package com.lwreid.platform.selenium.pages.old.account.sections.profile;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Nikita Ovsyannikov on 16.01.2017.
 */
public class MyAccount extends UserProfile {
    private WebDriverWait wait;

    public MyAccount() {
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "#users-email")
    WebElement emailInput;

    @FindBy(css = ".errorMessage")
    WebElement errorMessage;

    @FindBy(css = "#users-first_name")
    WebElement nameInput;

    @FindBy(css = "#users-last_name")
    WebElement lastNameInput;

    @FindBy(css = "#save")
    WebElement saveButton;

    @Step
    public MyAccount typeEmail(String email) {
       wait(500);
        typeText(emailInput, email);
        return this;
    }

    @Step
    public String getEmail() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput.getAttribute("value");
    }

    @Step
    public MyAccount typeName(String name) {
        wait(500);
        typeText(nameInput, name);
        return this;
    }

    @Step
    public String getName() {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        return nameInput.getAttribute("value");
    }

    @Step
    public MyAccount typeLastName(String lastName) {
        typeText(lastNameInput, lastName);
        return this;
    }

    @Step
    public String getLastName() {
        return lastNameInput.getAttribute("value");
    }

    @Step
    public boolean isErrorMessagePresented() {
        return isElementPresentedWithWait(errorMessage);
    }

    @Step
    public boolean isEmailMatches(String email) {
        String text = emailInput.getAttribute("value");
        return email.equals(text);
    }

    @Step
    public MyAccount clickSave() {
        if (saveButton.getAttribute("class").contains("disabled"))
            Assert.fail("Button is disabled!");
        saveButton.click();
        return this;
    }
}
