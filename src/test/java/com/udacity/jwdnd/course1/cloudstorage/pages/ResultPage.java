package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(id = "successContinue")
    private WebElement successButton;

    @FindBy(id = "failureContinue")
    private WebElement failureButton;

    @FindBy(id = "errorContinue")
    private WebElement errorButton;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickSuccessContinue() {
        successButton.click();
    }

    public void clickFailureContinue() {
        failureButton.click();
    }

    public void clickErrorContinue() {
        errorButton.click();
    }

}
