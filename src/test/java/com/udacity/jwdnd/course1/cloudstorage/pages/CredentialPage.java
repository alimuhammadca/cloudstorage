package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id = "credentialEditButton")
    private WebElement credentialEditButton;

    @FindBy(id = "credentialDeleteButton")
    private WebElement credentialDeleteButton;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsername;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPassword;

    @FindBy(id = "credential-id")
    private WebElement credentialId;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlValue;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameValue;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordValue;

    @FindBy(id = "saveCredential")
    private WebElement credentialSubmitButton;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setCredentialUrlValue(String url) {
        credentialUrlValue.clear();
        credentialUrlValue.sendKeys(url);
    }

    public String getCredentialUrlValue() {
        return credentialUrlValue.getText();
    }

    public void setCredentialUsernameValue(String username) {
        credentialUsernameValue.clear();
        credentialUsernameValue.sendKeys(username);
    }

    public String getCredentialUsernameValue() {
        return credentialUsernameValue.getText();
    }

    public void setCredentialPasswordValue(String password) {
        credentialPasswordValue.clear();
        credentialPasswordValue.sendKeys(password);
    }

    public String getCredentialPasswordValue() {
        return credentialPasswordValue.getAttribute("value");
    }

    public void clickCredentialSubmit() {
        credentialSubmitButton.click();
    }

    public String getCredentialId() {
        return credentialId.getText();
    }

    public String getCredentialUrl() {
        return credentialUrl.getText();
    }

    public String getCredentialUsername() {
        return credentialUsername.getText();
    }

    public String getCredentialPassword() {
        return credentialPassword.getText();
    }
    public void clickAddCredentialButton() {
        addCredentialButton.click();
    }

    public void clickCredentialEdit() {
        credentialEditButton.click();
    }

    public void clickCredentialDelete() {
        credentialDeleteButton.click();
    }

}
