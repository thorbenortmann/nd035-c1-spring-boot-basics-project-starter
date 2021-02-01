package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(css = "#continue-success")
    private WebElement successLink;

    private final WebDriver webDriver;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void acceptSuccess() throws InterruptedException {
        Thread.sleep(500);
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.successLink)).click();
    }
}
