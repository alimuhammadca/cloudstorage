package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupTests {
    public static final String SIGNUP_MESSAGE = "You successfully signed up!";
    public static final String USER_ALREADY_EXISTS = "The username already exists.";

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private SignupPage signupPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        webDriver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(webDriver);
    }

    /**
     * This method tests user signup
     */
    @Test
    public void userSignupTest() {
        signupPage.setFirstName("John");
        signupPage.setLastName("Doe");
        signupPage.setUsername("sa");
        signupPage.setPassword("123");
        signupPage.clickSignup();
        int n = SIGNUP_MESSAGE.length();
        assertTrue(signupPage.getSuccessMessage().contains(SIGNUP_MESSAGE));
    }

}
