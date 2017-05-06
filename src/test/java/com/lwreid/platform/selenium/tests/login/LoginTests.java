package com.lwreid.platform.selenium.tests.login;

import com.lwreid.platform.selenium.annotation.Page;
import com.lwreid.platform.selenium.listeners.TestListener;
import com.lwreid.platform.selenium.pages.BaseTest;
import com.lwreid.platform.selenium.pages.alerts.Alert;
import com.lwreid.platform.selenium.pages.myAccount.DashboardPage;
import com.lwreid.platform.selenium.pages.login.LoginPage;
import com.lwreid.platform.selenium.pages.main.MainPage;
import com.lwreid.platform.selenium.utils.DataProviderPool;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

/**
 * Created by Admin on 03.03.2017.
 */
@Listeners(TestListener.class)
@Features("Login Tests")
public class LoginTests extends BaseTest {

    @Page private static LoginPage loginPage;
    @Page private static MainPage mainPage;
    @Page private static Alert alert;
    @Page private static DashboardPage myAccountPage;

    @Test(dataProvider = DataProviderPool.INCORRECT_USER_CREDENTIALS, dataProviderClass = DataProviderPool.class)
    @TestCaseId("1")
    @Description("login with incorrect credentials")
    public void tc1Test(final String login, final String password) {

        String correctErrorMessage = "Invalid login or password.";

        mainPage.clickLogin();
        loginPage.typeLogin(login);
        loginPage.typePassword(password);
        String loginErrorMessage = alert.getAlertErrorMessage();
        Assert.assertEquals(loginErrorMessage, correctErrorMessage,"Login error message isn't valid");
    }

    @Test(dataProvider = DataProviderPool.USER_CREDENTIALS, dataProviderClass = DataProviderPool.class)
    @TestCaseId("2")
    @Description("login with correct credentials")
    public void tc2Test(final String login, final String password) {
        mainPage.clickLogin();
        loginPage.typeLogin(login);
        loginPage.typePassword(password);
        boolean dashboardPageOpened = myAccountPage.isDashboardPageOpened();
        Assert.assertTrue(dashboardPageOpened, "Dashboard page has not been opened.");
    }

    @Test(dataProvider = DataProviderPool.USER_CREDENTIALS, dataProviderClass = DataProviderPool.class)
    @TestCaseId("3")
    @Description("enter not valid email address")
    public void tc3Test(final String login, final String password) {

        String invalidEmail = login + "123";
        String correctEmailErrorMessage = "Please enter a valid email address. For example johndoe@domain.com.";

        mainPage.clickLogin();
        loginPage.typeLogin(invalidEmail);
        loginPage.typePassword(password);
        String emailErrorMessage = loginPage.getEmailErrorMessage();

        Assert.assertEquals(emailErrorMessage, correctEmailErrorMessage, "Validation email message isn't correct");
    }

    @Test
    @TestCaseId("4")
    @Description("enter not valid password")
    public void tc4Test() {

        String invalidPassword = "66";
        String correctPasswordErrorMessage = "Please enter 6 or more characters. Leading or trailing spaces will be ignored.";

        mainPage.clickLogin();
        loginPage.typePassword(invalidPassword);
        String passwordErrorMessage = loginPage.getPasswordErrorMessage();

        Assert.assertEquals(passwordErrorMessage, correctPasswordErrorMessage, "Validation password message isn't correct");
    }

    @Test
    @TestCaseId("5")
    @Description("Checking required fields")
    public void tc5Test() {

        String correctValidationErrorMessage = "This is a required field.";

        mainPage.clickLogin();
        loginPage.typePassword("");
        String passwordValidationErrorMessage = loginPage.getValidationEmailErrorMessage();
        String emailValidationErrorMessage = loginPage.getValidationPasswordErrorMessage();

        Assert.assertEquals(emailValidationErrorMessage, correctValidationErrorMessage, "Validation error message for Email Address field isn't correct");
        Assert.assertEquals(passwordValidationErrorMessage, correctValidationErrorMessage, "Validation error message for Password field isn't correct");
    }

}
