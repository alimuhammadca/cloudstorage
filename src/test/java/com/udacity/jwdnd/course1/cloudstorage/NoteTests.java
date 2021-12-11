package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.openqa.selenium.NoSuchElementException;

import static com.udacity.jwdnd.course1.cloudstorage.Util.delay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private NotePage notePage;
    private HomePage homePage;

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
    public void notesSignupTest() {
        //Signup a new user
        webDriver.get("http://localhost:" + port + "/signup");
        signupPage = new SignupPage(webDriver);
        signupPage.setFirstName("John");
        signupPage.setLastName("Doe");
        signupPage.setUsername("sa");
        signupPage.setPassword("123");
        signupPage.clickSignup();
    }

    @Test
    @Order(2)
    public void notesLoginTest() {
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
    public void notesAddTest() {
        //move to notes tab
        homePage = new HomePage(webDriver);
        homePage.clickNotesTab();
        notePage = new NotePage(webDriver);
        delay(1000);

        //add a new note
        notePage.clickAddNoteButton();
        delay(1000);
        notePage.setNoteTitle("title 1");
        notePage.setNoteDescription("desc 1");
        notePage.clickNoteSubmit();
        delay(1000);
        homePage.clickNotesTab();
        delay(1000);

        //verify that the new note is listed
        assertEquals("title 1", notePage.getNoteTitle());
        assertEquals("desc 1",notePage.getNoteDescription());

    }

    @Test
    @Order(4)
    public void notesEditTest() {
        //edit the note
        notePage = new NotePage(webDriver);
        notePage.clickNoteEdit();
        delay(1000);
        notePage.setNoteTitle("title 2");
        notePage.setNoteDescription("desc 2");
        notePage.clickNoteSubmit();
        delay(1000);
        homePage = new HomePage(webDriver);
        homePage.clickNotesTab();
        delay(1000);

        //verify that the changes are listed in the notes list
        assertEquals("title 2", notePage.getNoteTitle());
        assertEquals("desc 2", notePage.getNoteDescription());

    }

    @Test
    @Order(5)
    public void notesDeleteTest() {
        //delete the note
        notePage = new NotePage(webDriver);
        notePage.clickNoteDelete();
        delay(1000);

        //verify that the note is no longer visible
        assertThrows(NoSuchElementException.class, ()->notePage.getNoteTitle());
    }

}
