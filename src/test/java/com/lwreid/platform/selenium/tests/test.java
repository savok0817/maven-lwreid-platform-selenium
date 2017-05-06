package com.lwreid.platform.selenium.tests;

import com.lwreid.platform.selenium.annotation.Page;
import com.lwreid.platform.selenium.listeners.TestListener;
import com.lwreid.platform.selenium.pages.BaseTest;
import com.lwreid.platform.selenium.pages.main.MainPage;
import com.lwreid.platform.selenium.pages.login.SignInPopup;
import com.lwreid.platform.selenium.pages.old.account.sections.profile.MyPhotoBookSection;
import com.lwreid.platform.selenium.utils.DataProviderPool;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.Random;


@Listeners(TestListener.class)
public class test extends BaseTest {

    @Page
    private static MainPage mainPage;
    @Page private static SignInPopup signInPopup;
    @Page private static MyPhotoBookSection photoBookSection;

    @Test(dataProvider = DataProviderPool.USER_CREDENTIALS, dataProviderClass = DataProviderPool.class)
    @TestCaseId("")
    @Description("")
    public void tc13971Test(final String login, final String password) {

        String r_name = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));

        signInPopup
                .waitForPopup()
                .typeLogin(login)
                .typePassword(password)
                .clickLogin();

        photoBookSection.openRandomBook();
    }
}
