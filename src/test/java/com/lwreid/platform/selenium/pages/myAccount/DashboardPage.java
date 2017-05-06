package com.lwreid.platform.selenium.pages.myAccount;

import com.lwreid.platform.selenium.pages.BasePage;
import com.lwreid.platform.selenium.pages.login.LoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Admin on 08.03.2017.
 */
public class DashboardPage extends BasePage {
    private WebDriverWait wait;
    private Actions actions;

    public DashboardPage() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(xpath = "//h1[text()='Dashboard']")
    WebElement dashboardLabel;

    @Step
    public boolean isDashboardPageOpened(){
        return isElementPresentedWithWait(dashboardLabel);
    }
}
