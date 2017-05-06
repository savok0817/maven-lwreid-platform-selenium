package com.lwreid.platform.selenium.pages.alerts;

import com.lwreid.platform.selenium.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Admin on 06.05.2017.
 */
public class Alert extends BasePage {
    private WebDriverWait wait;
    private Actions actions;

    public Alert() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = ".alert-box.error")
    WebElement errorAlert;

    @Step
    public String getAlertErrorMessage(){
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOf(errorAlert));
        return errorMessage.getText().replaceAll("[\\n]+", "");
    }
}
