package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignupAndLoginTests {

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
        this.driver = new ChromeDriver();
        this.baseURL = "http://localhost:" + this.port + "/";
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * Tests that a user who is not logged in can only access the login- and signup-pages.
     */
    @Test
    public void testUnauthorizedUser() {
        // Arrange
        var paths = Arrays.asList("", "login", "signup", "home", "credential", "file", "note");
        var accessibleUrls = Arrays.asList(baseURL + "login", baseURL + "signup");

        // Act & Assert
        for (String path : paths) {
            this.driver.get(this.baseURL + path);
            var resultUrl = driver.getCurrentUrl();
            Assertions.assertTrue(accessibleUrls.contains(resultUrl));
        }
    }

    /**
     * Tests that user can access the 'home' page after signing up and logging in.
     * And that a user can not access the 'home' page after logging out anymore.
     */
    @Test
    public void testSignupAndLogin(){
        // Arrange
        var firstName = "testfirstname";
        var lastName = "testlastname";
        var userName = "testusername";
        var password = "testpassword";
        var homeUrl = baseURL + "home";

        // Act & Assert
        driver.get(baseURL + "signup");
        var signupPage = new SignupPage(this.driver);
        signupPage.signup(firstName, lastName, userName, password);

        driver.get(baseURL + "login");
        var loginPage = new LoginPage(this.driver);
        loginPage.login(userName, password);

        Assertions.assertEquals(homeUrl, driver.getCurrentUrl());

        var homePage = new HomePage(this.driver);
        homePage.logout();

        driver.get(baseURL + "home");
        Assertions.assertNotEquals(homeUrl, this.driver.getCurrentUrl());
    }
}
