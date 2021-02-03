package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    private static final String firstName = "testfirstname";
    private static final String lastName = "testlastname";
    private static final String userName = "testusername";
    private static final String password = "testpassword";
    private static int userId = 0;

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.baseURL = "http://localhost:" + this.port + "/";
        this.driver = new ChromeDriver();
        this.signupAndLoginTestUser(userId);
        userId++;
    }

    private void signupAndLoginTestUser(int userId) {
        var userName = NoteTests.userName+userId;

        this.driver.get(baseURL + "signup");
        var signupPage = new SignupPage(this.driver);
        signupPage.signup(firstName, lastName, userName, password);

        this.driver.get(baseURL + "login");
        var loginPage = new LoginPage(this.driver);
        loginPage.login(userName, password);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    /**
     * Test that notes can be created and are shown to the user.
     */
    @Test
    public void testCreateNote() throws InterruptedException {
        // Arrange
        var noteTitle = "Test Note";
        var noteDescription = "Test Note Description";

        // Act
        var homePage = new HomePage(driver);
        homePage.selectNotesTab();
        homePage.createNote(noteTitle, noteDescription);

        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();

        homePage.selectNotesTab();

        // Assert
        Assertions.assertTrue(homePage.getNoteTitles().contains(noteTitle));
        Assertions.assertTrue(homePage.getNoteDescriptions().contains(noteDescription));
    }

    /**
     * Tests that notes can be deleted.
     */
    @Test
    public void testDeleteNote() throws InterruptedException {
        // Arrange
        var noteTitle = "Test Note";
        var noteDescription = "Test Note Description";

        var homePage = new HomePage(driver);
        homePage.selectNotesTab();
        homePage.createNote(noteTitle, noteDescription);
        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();
        homePage.selectNotesTab();
        Assertions.assertTrue(homePage.getNoteTitles().contains(noteTitle));
        Assertions.assertTrue(homePage.getNoteDescriptions().contains(noteDescription));

        // Act
        homePage.deleteFirstNote();
        resultPage.acceptSuccess();
        homePage.selectNotesTab();

        // Assert
        Assertions.assertTrue(homePage.getNoteTitles().isEmpty());
        Assertions.assertTrue(homePage.getNoteDescriptions().isEmpty());
    }

    /**
     * Tests that notes can be edited.
     */
    @Test
    public void testEditNote() throws InterruptedException {
        // Arrange
        var oldNoteTitle = "Old Test Note";
        var oldNoteDescription = "Old Test Note Description";

        var homePage = new HomePage(driver);
        homePage.selectNotesTab();
        homePage.createNote(oldNoteTitle, oldNoteDescription);
        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();
        homePage.selectNotesTab();

        var newNoteTitle = "New Note Title";
        var newNoteDescription = "New Note Description";

        // Act
        homePage.editFirstNote(newNoteTitle, newNoteDescription);
        resultPage.acceptSuccess();
        homePage.selectNotesTab();

        // Assert
        Assertions.assertFalse(homePage.getNoteTitles().contains(oldNoteTitle));
        Assertions.assertFalse(homePage.getNoteDescriptions().contains(oldNoteDescription));
        Assertions.assertTrue(homePage.getNoteTitles().contains(newNoteTitle));
        Assertions.assertTrue(homePage.getNoteDescriptions().contains(newNoteDescription));
    }
}
