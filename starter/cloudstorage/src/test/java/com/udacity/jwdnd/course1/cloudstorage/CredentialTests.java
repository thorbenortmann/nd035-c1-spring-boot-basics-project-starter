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
public class CredentialTests {

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
        var userName = CredentialTests.userName+userId;

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
     * Test that credentials can be created and passwords are displayed encrypted to the user.
     */
    @Test
    public void testCreateCredential() throws InterruptedException {
        // Arrange
        var url = "url.com";
        var userName = "username";
        var password = "password";

        // Act
        var homePage = new HomePage(driver);
        homePage.selectCredentialsTab();
        homePage.createCredential(url, userName,password);

        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();

        homePage.selectCredentialsTab();

        // Assert
        Assertions.assertTrue(homePage.getCredentialUrls().contains(url));
        Assertions.assertTrue(homePage.getCredentialUserNames().contains(userName));
        Assertions.assertFalse(homePage.getEncryptedCredentialPasswords().contains(password));
        Assertions.assertFalse(homePage.getEncryptedCredentialPasswords().isEmpty());

    }

    /**
     * Tests that credentials can be edited and that passwords are shown decrypted during editing.
     */
    @Test
    public void testEditCredential() throws InterruptedException {
        // Arrange
        var oldUrl = "oldurl.com";
        var oldUserName = "oldusername";
        var oldPassword = "oldpassword";

        var homePage = new HomePage(driver);
        homePage.selectCredentialsTab();
        homePage.createCredential(oldUrl, oldUserName,oldPassword);
        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();
        homePage.selectCredentialsTab();

        var newUrl = "newurl.com";
        var newUserName = "newusername";
        var newPassword = "newpassword";

        // Act & Assert
        homePage.openEditModalForFirstCredential();
        Assertions.assertEquals(oldPassword, homePage.getDecryptedPassword());

        homePage.editCredential(newUrl, newUserName, newPassword);
        resultPage.acceptSuccess();
        homePage.selectCredentialsTab();

        // Assert
        Assertions.assertFalse(homePage.getCredentialUrls().contains(oldUrl));
        Assertions.assertFalse(homePage.getCredentialUserNames().contains(oldUserName));
        Assertions.assertFalse(homePage.getEncryptedCredentialPasswords().contains(oldPassword));

        Assertions.assertTrue(homePage.getCredentialUrls().contains(newUrl));
        Assertions.assertTrue(homePage.getCredentialUserNames().contains(newUserName));
        Assertions.assertFalse(homePage.getEncryptedCredentialPasswords().contains(newPassword));
        Assertions.assertFalse(homePage.getEncryptedCredentialPasswords().isEmpty());
    }

    /**
     * Tests that credentials can be deleted.
     */
    @Test
    public void testDeleteCredential() throws InterruptedException {
        // Arrange
        var url = "url.com";
        var userName = "username";
        var password = "password";

        var homePage = new HomePage(driver);
        homePage.selectCredentialsTab();
        homePage.createCredential(url, userName,password);
        var resultPage = new ResultPage(driver);
        resultPage.acceptSuccess();
        homePage.selectCredentialsTab();

        // Act
        homePage.deleteFirstCredential();
        resultPage.acceptSuccess();
        homePage.selectCredentialsTab();

        // Assert
        Assertions.assertTrue(homePage.getCredentialUrls().isEmpty());
        Assertions.assertTrue(homePage.getCredentialUserNames().isEmpty());
        Assertions.assertTrue(homePage.getEncryptedCredentialPasswords().isEmpty());


    }

}