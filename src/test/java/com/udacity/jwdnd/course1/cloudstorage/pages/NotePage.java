package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.validation.constraints.NotNull;

public class NotePage {
    @FindBy(id = "noteEditButtons")
    private WebElement[] noteEditButtons;

    @FindBy(id = "noteDeleteButtons")
    private WebElement[] noteDeleteButtons;

    @FindBy(id = "noteTitles")
    private WebElement[] noteTitles;

    @FindBy(id = "noteDescriptions")
    private WebElement[] noteDescriptions;

    @FindBy(id = "note-id")
    private WebElement noteId;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setNoteTitle(String title) {
        noteTitle.sendKeys(title);
    }

    public void setNoteDescription(String description) {
        noteDescription.sendKeys(description);
    }

    public void clickNoteSubmit() {
        noteSubmitButton.click();
    }

    public String getNoteTitle(@NotNull Integer index) {
        return noteTitles[index].getText();
    }

    public String getNoteDescription(@NotNull Integer index) {
        return noteDescriptions[index].getText();
    }
}
