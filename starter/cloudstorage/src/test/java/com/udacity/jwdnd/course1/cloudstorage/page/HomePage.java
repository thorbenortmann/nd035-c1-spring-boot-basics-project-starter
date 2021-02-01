package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    @FindBy(css="#logout-form")
    private WebElement logoutForm;

    @FindBy(css="#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css="#new-note-button")
    private WebElement newNoteButton;

    @FindBy(css="#note-title")
    private WebElement noteTitle;

    @FindBy(css="#note-description")
    private WebElement noteDescription;

    @FindBy(css="#note-save-changes-button")
    private WebElement noteSubmitButton;

    @FindBy(css="#note-table-body")
    private WebElement noteTableBody;

    @FindBy(css=".note-title")
    private List<WebElement> noteTitles;

    @FindBy(css=".note-description")
    private List<WebElement> noteDescriptions;

    private final WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void logout() {
        var wait =  new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(this.logoutForm));
        this.logoutForm.submit();
    }

    public void createNote(String noteTitle, String noteDescription) {

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.newNoteButton)).click();

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.noteTitle)).sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public void selectNotesTab() throws InterruptedException {
        Thread.sleep(500);
        new WebDriverWait(this.webDriver, 5).until(ExpectedConditions.elementToBeClickable(this.notesTab)).click();
    }

    public List<String> getNoteTitles() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        return this.noteTitles.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getNoteDescriptions() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        return this.noteDescriptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
