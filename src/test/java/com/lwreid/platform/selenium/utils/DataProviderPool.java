package com.lwreid.platform.selenium.utils;

import org.testng.annotations.DataProvider;

public class DataProviderPool {
    public static final String USER_CREDENTIALS = "userCredentials";

    @DataProvider(name = USER_CREDENTIALS)
    public static Object[][] getUserEmailData() {
        return new Object[][] {
                {"oksana.savchenko@ewave.com", "123456"}
        };
    }

    public static final String INCORRECT_USER_CREDENTIALS = "incorrectUserCredentials";

    @DataProvider(name = INCORRECT_USER_CREDENTIALS)
    public static Object[][] getIncorrectUserEmailData() {
        return new Object[][] {
                {"oksana.savchenko123@ewave.com", "123456"}
        };
    }
}
