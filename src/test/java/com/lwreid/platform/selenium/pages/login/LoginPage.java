package com.lwreid.platform.selenium.pages.login;

import com.lwreid.platform.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Admin on 03.03.2017.
 */
public class LoginPage extends BasePage {
    private WebDriverWait wait;
    private Actions actions;

    public LoginPage() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "#email")
    WebElement loginInput;

    @FindBy(css = "#pass")
    WebElement passwordInput;

    @Step
    public LoginPage typeLogin(String login) {
        wait.until(ExpectedConditions.visibilityOf(loginInput));
        typeText(loginInput, login);
        return this;
    }

    @Step
    public LoginPage typePassword(String password) {
        typeText(passwordInput, password);
        passwordInput.submit();
        return this;
    }

    @Step
    public String getEmailErrorMessage(){
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-validate-email-email")));
        return errorMessage.getText();
    }

    @Step
    public String getPasswordErrorMessage(){
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-validate-password-pass")));
        return errorMessage.getText();
    }

    @Step
    public String getValidationEmailErrorMessage(){
        WebElement errorValidation1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-required-entry-email")));
        return errorValidation1.getText();
    }

    @Step
    public String getValidationPasswordErrorMessage(){
        WebElement errorValidation2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("advice-required-entry-pass")));
        return errorValidation2.getText();
    }
}
