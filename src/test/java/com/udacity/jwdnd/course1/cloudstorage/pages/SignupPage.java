package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameValueField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameValueField;

    @FindBy(id = "inputUsername")
    private WebElement usernameValueField;

    @FindBy(id = "inputPassword")
    private WebElement passwordValueField;

    @FindBy(id = "signupButton")
    private WebElement signupButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public void setUsername(String username) {
        usernameValueField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordValueField.sendKeys(password);
    }

    public void clickSignup() {
        signupButton.click();
    }

}
