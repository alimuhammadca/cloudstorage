package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {
    @FindBy(id = "credentialEditButtons")
    private WebElement[] credentialEditButtons;

    @FindBy(id = "credentialDeleteButtons")
    private WebElement[] credentialDeleteButtons;

    @FindBy(id = "credentialUrls")
    private WebElement[] credentialUrls;

    @FindBy(id = "credentialUsernames")
    private WebElement[] credentialUsernames;

    @FindBy(id = "credentialPasswords")
    private WebElement[] credentialPasswords;

    @FindBy(id = "credential-id")
    private WebElement credentialId;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setCredentialUrl(String url) {
        credentialUrl.sendKeys(url);
    }

    public void setCredentialUsername(String username) {
        credentialUsername.sendKeys(username);
    }

    public void setCredentialPassword(String password) {
        credentialPassword.sendKeys(password);
    }

    public void clickCredentialSubmit() {
        credentialSubmitButton.click();
    }

    public String getCredentialUrl(@NotNull Integer index) {
        return credentialUrls[index].getText();
    }

    public String getCredentialUsername(@NotNull Integer index) {
        return credentialUsernames[index].getText();
    }

    public String getCredentialPassword(@NotNull Integer index) {
        return credentialPasswords[index].getText();
    }
}
