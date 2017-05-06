package com.lwreid.platform.selenium.pages.login;

import com.lwreid.platform.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Nikita Ovsyannikov on 16.01.2017.
 */
public class SignInPopup extends BasePage {
    private WebDriverWait wait;
    private Actions actions;

    public SignInPopup() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "#login-form input[id*='email']")
    WebElement loginInput;

    @FindBy(css = "#login-form input[id*='password']")
    WebElement passwordInput;

    /*@FindBy(xpath = Locators.SIGN_IN_BUTTON)
    WebElement loginButton;*/

    @FindBy(css = "#forgot-pass")
    WebElement forgotPassword;

    @Step
    public SignInPopup waitForPopup() {
        //super.waitForPopup();
        return this;
    }

    @Step
    public SignInPopup typeLogin(String login) {
        wait.until(ExpectedConditions.visibilityOf(loginInput));
        typeText(loginInput, login);
        return this;
    }

    @Step
    public SignInPopup typePassword(String password) {
        typeText(passwordInput, password);
        return this;
    }

    @Step
    public SignInPopup clickLogin() {
        //actions.click(loginButton).build().perform();
        //waaitForNetwork(10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#login-form")));
        return this;
    }

    @Step
    public SignInPopup clickForgotPassword() {
        forgotPassword.click();
        return this;
    }

    @Step
    public boolean isSuccessOperationMessagePresented() {
        return isElementPresentedWithWait(By.cssSelector(".jnotify-notification-success"));
    }
}
