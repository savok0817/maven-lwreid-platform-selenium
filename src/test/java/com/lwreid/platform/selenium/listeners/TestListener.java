package com.lwreid.platform.selenium.listeners;

import com.lwreid.platform.selenium.pages.BasePage;
import com.lwreid.platform.selenium.annotation.Page;
import com.lwreid.platform.selenium.pages.BaseTest;
import com.lwreid.platform.selenium.pages.main.ObjectPool;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        attachScreenshot();
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        byte[] screenshotAs = null;
        try {
            screenshotAs = ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            fail(e);
        }
        return screenshotAs;
    }

    @Attachment(value = "Element screenshot", type = "image/png")
    public static byte[] attachScreenshot(Screenshot screenshot) {
        byte[] screenshotAs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "png", baos);
            screenshotAs = baos.toByteArray();
        } catch (Exception ignored) {
        }
        return screenshotAs;
    }

    @Attachment(value = "Marked Image diff", type = "image/png")
    public static byte[] attachScreenshot(ImageDiff screenshot) {
        byte[] screenshotAs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getMarkedImage(), "png", baos);
            screenshotAs = baos.toByteArray();
        } catch (Exception ignored) {
        }
        return screenshotAs;
    }

    @Attachment(value = "Unable to save screenshot")
    private String fail(Exception e) {
        return String.format("%s\n%s", e.getMessage(), Arrays.toString(e.getStackTrace()));
    }

    /**
     * [@Page] annotation processing;
     * Injects your PageObject into Test class.
     * Can be used only inside Test class.
     * @param iTestResult -> XD
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onTestStart(ITestResult iTestResult) {
        Field[] declaredFields = iTestResult.getTestClass().getRealClass().getDeclaredFields();
        Set<Field> collect = Stream.of(declaredFields).filter(h -> h.isAnnotationPresent(Page.class)).collect(Collectors.toSet());
        if (collect.size() > 0) {
            collect.forEach(h -> {
                try {
                    h.setAccessible(true);
                    Class aClass = Class.forName(h.getType().getName());
                    BasePage instance = ObjectPool.getInstance().getObject(aClass);
                    Object o = h.get(aClass);
                    h.set(o, instance);
                } catch (IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}