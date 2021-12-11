package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static com.udacity.jwdnd.course1.cloudstorage.Util.delay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private CredentialPage credentialPage;
    private HomePage homePage;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    @Autowired
    public void setCredentialService(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        webDriver.quit();
    }

    @Test
    @Order(1)
    public void credentialSignupTest() {
        //Signup a new user
        webDriver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(webDriver);
        signupPage.setFirstName("John");
        signupPage.setLastName("Doe");
        signupPage.setUsername("sa");
        signupPage.setPassword("123");
        signupPage.clickSignup();
        webDriver.get("http://localhost:" + port + "/login");
        assertEquals("Login", webDriver.getTitle());
    }

    @Test
    @Order(2)
    public void credentialLoginTest() {
        //Sign-in the user
        webDriver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(webDriver);
        loginPage.setUsername("sa");
        loginPage.setPassword("123");
        loginPage.clickLogin();

        //verify that the authenticated user can access home page
        assertEquals("Home", webDriver.getTitle());
    }

    @Test
    @Order(3)
    public void credentialAddTest() {

        //move to credentials tab
        homePage = new HomePage(webDriver);
        homePage.clickCredentialsTab();
        credentialPage = new CredentialPage(webDriver);
        delay(1000);

        //add a new set of credentials
        credentialPage.clickAddCredentialButton();
        delay(1000);
        credentialPage.setCredentialUrlValue("url 1");
        credentialPage.setCredentialUsernameValue("username 1");
        credentialPage.setCredentialPasswordValue("password 1");
        delay(1000);
        credentialPage.clickCredentialSubmit();
        homePage.clickCredentialsTab();
        delay(1000);

        //verify that the new credential is listed
        assertEquals("url 1", credentialPage.getCredentialUrl());
        assertEquals("username 1", credentialPage.getCredentialUsername());
        Credential credential = credentialService.getCredentialByUsername("username 1");
        String key = credential.getKey();
        assertEquals(encryptionService.encryptValue("password 1", key),
                credentialPage.getCredentialPassword());
    }

    @Test
    @Order(4)
    public void credentialEditTest() {

        //edit the credentials
        credentialPage = new CredentialPage(webDriver);
        credentialPage.clickCredentialEdit();
        delay(1000);

        //checks that password is unencrypted
        assertEquals("password 1", credentialPage.getCredentialPasswordValue());

        //modify credential values
        credentialPage.setCredentialUrlValue("url 2");
        credentialPage.setCredentialUsernameValue("username 2");
        credentialPage.setCredentialPasswordValue("password 2");
        credentialPage.clickCredentialSubmit();
        delay(1000);
        homePage = new HomePage(webDriver);
        homePage.clickCredentialsTab();
        delay(1000);

        //verify that the changes are listed in the credentials list
        assertEquals("url 2", credentialPage.getCredentialUrl());
        assertEquals("username 2", credentialPage.getCredentialUsername());
        Credential credential = credentialService.getCredentialByUsername("username 2");
        String key = credential.getKey();
        assertEquals(encryptionService.encryptValue("password 2", key),
                credentialPage.getCredentialPassword());
    }

    @Test
    @Order(5)
    public void credentialDeleteTest() {
        //delete the credentials
        credentialPage = new CredentialPage(webDriver);
        credentialPage.clickCredentialDelete();

        //verify that the set of credentials is no longer visible
        assertThrows(NoSuchElementException.class, ()->credentialPage.getCredentialUrl());
    }

}
