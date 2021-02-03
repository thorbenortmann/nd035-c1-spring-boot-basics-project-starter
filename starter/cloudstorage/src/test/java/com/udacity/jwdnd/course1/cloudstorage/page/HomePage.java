package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;
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

    @FindBy(css=".note-edit-button")
    private List<WebElement> editNoteButtons;

    @FindBy(css=".note-delete-button")
    private List<WebElement> deleteNoteButtons;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(css="#new-credential-button")
    private WebElement newCredentialButton;

    @FindBy(css="#credential-url")
    private WebElement credentialUrl;

    @FindBy(css="#credential-username")
    private WebElement credentialUserName;

    @FindBy(css="#credential-password")
    private WebElement credentialPassword;

    @FindBy(css="#credential-save-changes-button")
    private WebElement credentialSubmitButton;

    @FindBy(css="#credentialTable")
    private WebElement credentialTableBody;

    @FindBy(css=".credential-url")
    private List<WebElement> credentialUrls;

    @FindBy(css=".credential-username")
    private List<WebElement> credentialUserNames;

    @FindBy(css=".encrypted-credential-password")
    private List<WebElement> encryptedPasswords;

    @FindBy(css=".credential-edit-button")
    private List<WebElement> editCredentialButtons;

    @FindBy(css=".credential-delete-button")
    private List<WebElement> deleteCredentialButtons;

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

    public void selectNotesTab() throws InterruptedException {
        Thread.sleep(500);
        new WebDriverWait(this.webDriver, 5).until(ExpectedConditions.elementToBeClickable(this.notesTab)).click();
    }

    public void createNote(String noteTitle, String noteDescription) {

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.newNoteButton)).click();

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.noteTitle)).sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitButton.click();
    }

    public List<String> getNoteTitles() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        return this.noteTitles.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getNoteDescriptions() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        return this.noteDescriptions.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void editFirstNote(String newNoteTitle, String newNoteDescription) {

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        Optional<WebElement> firstEditNoteButton = this.editNoteButtons.stream().findFirst();
        firstEditNoteButton.ifPresent(WebElement::click);

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.noteTitle));
        this.noteTitle.clear();
        this.noteTitle.sendKeys(newNoteTitle);

        this.noteDescription.clear();
        this.noteDescription.sendKeys(newNoteDescription);
        this.noteSubmitButton.click();
    }

    public void deleteFirstNote() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.noteTableBody));
        Optional<WebElement> firstDeleteNoteButton = this.deleteNoteButtons.stream().findFirst();
        firstDeleteNoteButton.ifPresent(WebElement::click);
    }

    public void selectCredentialsTab() throws InterruptedException {
        Thread.sleep(500);
        new WebDriverWait(this.webDriver, 5).until(ExpectedConditions.elementToBeClickable(this.credentialsTab)).click();

    }

    public void createCredential(String url, String userName, String password) {

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.newCredentialButton)).click();

        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.credentialUrl)).sendKeys(url);
        this.credentialUserName.sendKeys(userName);
        this.credentialPassword.sendKeys(password);
        this.credentialSubmitButton.click();
    }

    public List<String> getCredentialUrls() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.credentialTableBody));
        return this.credentialUrls.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getCredentialUserNames() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.credentialTableBody));
        return this.credentialUserNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getEncryptedCredentialPasswords() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.credentialTableBody));
        return this.encryptedPasswords.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void openEditModalForFirstCredential() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.credentialTableBody));
        Optional<WebElement> firstEditCredentialButton = this.editCredentialButtons.stream().findFirst();
        firstEditCredentialButton.ifPresent(WebElement::click);
    }

    public String getDecryptedPassword(){
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.credentialPassword));
        return this.credentialPassword.getAttribute("value");
    }

    public void editCredential(String url, String userName, String password) {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.elementToBeClickable(this.credentialUrl));
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(url);

        this.credentialUserName.clear();
        this.credentialUserName.sendKeys(userName);

        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);
        
        this.credentialSubmitButton.click();
    }

    public void deleteFirstCredential() {
        new WebDriverWait(webDriver, 2).until(ExpectedConditions.visibilityOf(this.credentialTableBody));
        Optional<WebElement> firstDeleteCredentialButton = this.deleteCredentialButtons.stream().findFirst();
        firstDeleteCredentialButton.ifPresent(WebElement::click);
    }
}
