package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {
    @FindBy(id = "noteEditButton")
    private WebElement noteEditButton;

    @FindBy(id = "noteDeleteButton")
    private WebElement noteDeleteButton;

    @FindBy(id = "noteTitle")
    private WebElement noteTitle;

    @FindBy(id = "noteDescription")
    private WebElement noteDescription;

    @FindBy(id = "note-id")
    private WebElement noteId;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleValue;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionValue;

    @FindBy(id = "saveNote")
    private WebElement noteSubmitButton;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setNoteTitle(String title) {
        noteTitleValue.clear();
        noteTitleValue.sendKeys(title);
    }

    public void setNoteDescription(String description) {
        noteDescriptionValue.clear();
        noteDescriptionValue.sendKeys(description);
    }

    public void clickAddNoteButton() {
        addNoteButton.click();
    }

    public void clickNoteSubmit() {
        noteSubmitButton.click();
    }

    public String getNoteTitle() {
        return noteTitle.getText();
    }

    public String getNoteDescription() {
        return noteDescription.getText();
    }

    public void clickNoteEdit() {
        noteEditButton.click();
    }

    public void clickNoteDelete() {
        noteDeleteButton.click();
    }

}
