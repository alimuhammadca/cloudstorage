package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessage;

    @FindBy(id = "inputUsername")
    private WebElement usernameValueField;

    @FindBy(id = "inputPassword")
    private WebElement passwordValueField;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getLogoutMessage() {
        return logoutMessage.getText();
    }

    public void setUsername(String username) {
        usernameValueField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordValueField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void clickSignup() {
        signupLink.click();
    }
}
