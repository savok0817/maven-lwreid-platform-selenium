package com.lwreid.platform.selenium.pages.old.account.sections.profile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;
import java.util.Random;

/**
 * Created by Nikita Ovsyannikov on 16.01.2017.
 */
public class MyPhotoBookSection extends UserProfile {
    private WebDriverWait wait;
    private Actions actions;

    public MyPhotoBookSection() {
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), 5);
    }

    @FindBy(css = "#userContent")
    WebElement container;

    @Step
    public boolean isMyPhotobookContainerOpened() {
        wait.until(ExpectedConditions.visibilityOf(container));
        WebElement photos = getDriver().findElement(By.cssSelector("#projects"));
        boolean contains = photos.getAttribute("class").contains("active");
        boolean elementPresented = isElementPresented(container);
        return contains && elementPresented;
    }

    @Step
    public MyPhotoBookSection openRandomBook() {
        List<WebElement> until =
                wait.until(ExpectedConditions.
                        presenceOfAllElementsLocatedBy(By.cssSelector(".items.projects button[data-url*='/book/edit']")));
        WebElement element = until.get(new Random().nextInt(until.size()));
        scrollToElement(element, true);
        wait(500);
        actions.click(element).build().perform();
        waitForNetwork(20);
        return this;
    }
}
